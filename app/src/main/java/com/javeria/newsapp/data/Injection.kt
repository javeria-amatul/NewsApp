package com.javeria.newsapp.data

import android.content.Context
import com.javeria.newsapp.App
import com.javeria.newsapp.data.local.db.AppDatabase
import com.javeria.newsapp.data.local.db.RoomDBHelper
import com.javeria.newsapp.data.parser.AppParser
import com.javeria.newsapp.data.remote.ApiService
import com.javeria.newsapp.data.remote.AppApiHelper

object Injection {

    fun provideDataRepository(context: Context) : DataSourceInterface {
        val database = AppDatabase.getInstance(App.applicationContext())
        return DataRepository.getInstance(
            AppApiHelper(ApiService.create()),
            AppParser.getInstance(),
            RoomDBHelper.getInstance(database)
        )
    }
}