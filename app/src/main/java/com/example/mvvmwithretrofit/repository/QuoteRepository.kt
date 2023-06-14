package com.example.mvvmwithretrofit.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmwithretrofit.api.QuoteService
import com.example.mvvmwithretrofit.db.QuoteDatabase
import com.example.mvvmwithretrofit.models.QuoteList
import com.example.mvvmwithretrofit.utils.NetworkUtils

class QuoteRepository(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
   private val applicationContext: Context
) {

    private val quotesLiveData = MutableLiveData<Response<QuoteList>>()
    val quotes: LiveData<Response<QuoteList>>
        get() = quotesLiveData

    suspend fun getQuotes(page: Int) {
        if(NetworkUtils.isInternetAvailable(applicationContext))
        {
            try {
                val result = quoteService.getQuotes(page)
                if (result?.body() != null) {

                    quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
                    quotesLiveData.postValue(Response.Success(result.body()))
                }
                else{
                    quotesLiveData.postValue(Response.Error("API Error"))
                }


            }catch (e:Exception){
                quotesLiveData.postValue(Response.Error(e.message.toString()))
            }

        }
        else{

            val quote=quoteDatabase.quoteDao().getQuotes()
            val quotelist = QuoteList(1,1,1,quote,1,1)
            quotesLiveData.postValue(Response.Success(quotelist))

        }


    }

    suspend fun getQuotesBackground(){
        val randomNumber = (Math.random() * 10).toInt()
        val result = quoteService.getQuotes(randomNumber)
        if (result?.body()!= null){
            quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
        }
    }
}