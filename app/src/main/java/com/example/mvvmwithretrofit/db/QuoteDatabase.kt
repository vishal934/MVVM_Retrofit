package com.example.mvvmwithretrofit.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [com.example.mvvmwithretrofit.models.Result::class], version = 1)
abstract class QuoteDatabase :RoomDatabase() {
    abstract fun quoteDao(): QuoteDao

    companion object{
        @Volatile
        private var INTANCE:QuoteDatabase?=null
        fun getdatabase(context: Context):QuoteDatabase{
            if(INTANCE==null){
                synchronized(this){
                    INTANCE = Room.databaseBuilder(context,QuoteDatabase::class.java,"quoteDB").build()
                }
            }
        return INTANCE!!
        }

    }
}
