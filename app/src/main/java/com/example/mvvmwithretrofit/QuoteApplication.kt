package com.example.mvvmwithretrofit

import android.app.Application
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.mvvmwithretrofit.api.QuoteService
import com.example.mvvmwithretrofit.api.RetrofitHelper
import com.example.mvvmwithretrofit.db.QuoteDatabase
import com.example.mvvmwithretrofit.repository.QuoteRepository
import com.example.mvvmwithretrofit.worker.QuoteWorker
import java.util.concurrent.TimeUnit

class QuoteApplication:Application() {
    lateinit var quoteRepository: QuoteRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
 setupWorker()
    }

    private fun setupWorker(){
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest = PeriodicWorkRequest.Builder(QuoteWorker::class.java,30,TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
  private  fun initialize(){
        val quoteService= RetrofitHelper.instance().create(QuoteService::class.java)
        val database= QuoteDatabase.getdatabase(applicationContext)
        quoteRepository = QuoteRepository(quoteService,database,applicationContext)

    }
}