package com.javeria.newsapp.data.parser

import androidx.annotation.VisibleForTesting
import com.javeria.newsapp.data.local.db.entity.Article
import com.javeria.newsapp.data.local.db.entity.News

class AppParser : IParser {

    companion object {
        private var INSTANCE: IParser? = null

        @JvmStatic
        fun getInstance(): IParser {
            if (INSTANCE == null) {
                synchronized(AppParser::javaClass) {
                    INSTANCE = AppParser()
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

    override fun parseNewsJSON(newsResponseData: News): List<Article> {
        val articleList = ArrayList<Article>()
        val currentTime = System.currentTimeMillis()
        val articleData = newsResponseData.articleList
        try {

            for(data in articleData!!){
                val article = Article()
                article.source = data.source
                article.sourceName = data.source!!.name
                article.publishedAt =  data.publishedAt!!.substring(0,10)
                article.title = data.title
                article.urlToImage = data.urlToImage
                article.url = data.url
                article.content = data.content
                article.description = data.description
                article.articleInsertionTime = currentTime
                articleList.add(article)
            }
        } catch (throwable: Throwable){
            throwable.printStackTrace()
        }
        return articleList

    }

}