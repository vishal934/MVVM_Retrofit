package com.example.mvvmwithretrofit

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmwithretrofit.api.QuoteService
import com.example.mvvmwithretrofit.api.RetrofitHelper
import com.example.mvvmwithretrofit.repository.QuoteRepository
import com.example.mvvmwithretrofit.repository.Response
import com.example.mvvmwithretrofit.viewmodels.MainViewModel
import com.example.mvvmwithretrofit.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // val quoteService = RetrofitHelper.instance().create(QuoteService::class.java)
        //val repository = QuoteRepository(quoteService)
        val repository =(application as QuoteApplication).quoteRepository
        mainViewModel =ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)
        mainViewModel.quotes.observe(this, Observer {

            when(it){
                is Response.Loading ->{}
                is Response.Success ->{
                    it.data?.let {
                        Toast.makeText(this@MainActivity, it.results.size.toString(),Toast.LENGTH_LONG).show()
                    }
                   // Toast.makeText(this@MainActivity, it.data?.results?.size.toString(),Toast.LENGTH_LONG).show()
                }
                is Response.Error ->{
                    Toast.makeText(this@MainActivity,"Some error message",Toast.LENGTH_LONG).show()
                }
            }

        })
    }
}