package com.javeria.newsapp.data

import com.javeria.newsapp.data.local.db.entity.Article
import io.reactivex.Single

interface DataSourceInterface{

    fun getArticleList(url: String, country: String, apiKey: String, hardRefresh: Boolean): Single<List<Article>>

    fun getArticleDetails(id: Long) : Single<Article>
}