package com.javeria.newsapp.data.local.db

import com.javeria.newsapp.data.local.db.entity.Article
import io.reactivex.Completable
import io.reactivex.Single

interface DBHelper{

    fun getArticlesFromDb() : Single<List<Article>>

    fun saveArticleListInDb(articleList: List<Article>) : Completable

    fun getArticleDetailFromDb(id: Long) : Single<Article>

    fun deleteArticlesForPreviousDates(newContentInsertionTime: Long): Completable
}