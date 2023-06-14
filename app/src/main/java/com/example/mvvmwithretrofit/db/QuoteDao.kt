package com.example.mvvmwithretrofit.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mvvmwithretrofit.models.Result

@Dao
interface QuoteDao {

    @Insert
    suspend fun addQuotes(quotes:List<Result>)

    @Query("SELECT * FROM quotes")
    suspend fun getQuotes():List<Result>
}