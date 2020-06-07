package com.javeria.newsapp.data.remote

import com.javeria.newsapp.data.local.db.entity.News
import io.reactivex.Single

interface ApiHelper{

    fun getArticlesListFromServer(url: String, country: String, apiKey: String) : Single<News>

}