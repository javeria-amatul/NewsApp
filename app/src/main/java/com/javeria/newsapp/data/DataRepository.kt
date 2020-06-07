package com.javeria.newsapp.data

import android.util.Log
import com.javeria.newsapp.Const
import com.javeria.newsapp.data.local.db.DBHelper
import com.javeria.newsapp.data.local.db.entity.Article
import com.javeria.newsapp.data.parser.IParser
import com.javeria.newsapp.data.remote.ApiHelper
import com.javeria.newsapp.data.remote.AppApiHelper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class DataRepository(
    val apiHelper: ApiHelper,
    val parser: IParser,
    val dbHelper: DBHelper
) : DataSourceInterface {

    companion object {

        private var INSTANCE: DataRepository? = null
        private val TAG = DataRepository.javaClass.canonicalName
        /**
         * Returns the single instance of this class, creating it if necessary.
         */
        @JvmStatic
        fun getInstance(
            appApiHelper: AppApiHelper,
            appParser: IParser,
            dbHelper: DBHelper
        ): DataRepository {
            return INSTANCE ?: DataRepository(appApiHelper, appParser, dbHelper)
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun getArticleList(
        url: String,
        country: String,
        apiKey: String,
        hardRefresh: Boolean
    ): Single<List<Article>> {

        val articlesFromServer = apiHelper.getArticlesListFromServer(url, country, apiKey)
            .flatMap { res ->
                if (res.articleList!!.isNotEmpty()) {
                    val articleList = parser.parseNewsJSON(res)
                    if (articleList.isNotEmpty()) {
                        val newArticleInsertionTime = articleList[0].articleInsertionTime
                        deleteOldArticles(newArticleInsertionTime)
                        dbHelper.saveArticleListInDb(articleList)
                    }
                }
                getInAppContentFromDatabase()
            }.onErrorReturn {
                dbHelper.getArticlesFromDb().subscribeOn(Schedulers.newThread()).blockingGet()
            }

        val articlesFromDb = getInAppContentFromDatabase().doOnError { }

        var mergedListData : List<Single<List<Article>>>
        if (hardRefresh)
            mergedListData = listOf(articlesFromServer, articlesFromDb)
        else
            mergedListData = listOf(articlesFromDb, articlesFromServer)

        return Single.concat(mergedListData.toMutableList())
            .subscribeOn(Schedulers.newThread())
            .filter { res -> res.isNotEmpty() }
            .first(ArrayList())
            .onErrorReturn {
                Log.e(TAG, "Error fetching articles data")
                ArrayList()
            }

    }


    private fun isExpired(oldContentInsertionTime: Long): Boolean {
        return (System.currentTimeMillis() - oldContentInsertionTime) >= Const.EXPIRE_IN
    }


    private fun getInAppContentFromDatabase(): Single<List<Article>> {
        return dbHelper.getArticlesFromDb()
            .flatMap { res ->
                if (res.isNotEmpty()) {
                    val article = res.get(0)
                    val oldContentInsertionTime = article.articleInsertionTime
                    if (isExpired(oldContentInsertionTime)) {
                        Single.just(ArrayList<Article>())
                    } else {
                        Single.just(res)
                    }
                } else {
                    Single.just(ArrayList())
                }
            }.onErrorReturn { ArrayList() }
    }

    override fun getArticleDetails(id: Long): Single<Article> {
        return dbHelper.getArticleDetailFromDb(id).flatMap { res ->
            if (isExpired(res.articleInsertionTime)) Single.just(Article()) else Single.just(res)
        }.onErrorReturn { Article() }
    }

    private fun deleteOldArticles(newContentInsertionTime: Long) {
        dbHelper.deleteArticlesForPreviousDates(newContentInsertionTime)
            .subscribe({}, {})
    }
}
