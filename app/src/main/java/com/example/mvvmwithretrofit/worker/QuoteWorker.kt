package com.example.mvvmwithretrofit.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mvvmwithretrofit.QuoteApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteWorker(private val context: Context,params: WorkerParameters):Worker(context,params) {
    @SuppressLint("SuspiciousIndentation")
    override fun doWork(): Result {
     val repository =(context as QuoteApplication).quoteRepository

        CoroutineScope(Dispatchers.IO).launch {
            repository.getQuotesBackground()
        }
        return Result.success()
    }
}