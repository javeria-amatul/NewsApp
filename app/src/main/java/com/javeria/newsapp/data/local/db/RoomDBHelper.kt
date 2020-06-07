package com.javeria.newsapp.data.local.db

import com.javeria.newsapp.data.local.db.entity.Article
import io.reactivex.Completable
import io.reactivex.Single

class RoomDBHelper private constructor(val database: AppDatabase):
    DBHelper {

    companion object {
        private var INSTANCE: RoomDBHelper? = null

        @JvmStatic
        fun getInstance(database: AppDatabase): RoomDBHelper {
            if (INSTANCE == null) {
                synchronized(RoomDBHelper::javaClass) {
                    INSTANCE =
                        RoomDBHelper(database)
                }
            }
            return INSTANCE!!
        }
    }

    override fun getArticlesFromDb(): Single<List<Article>> {
        return Single.fromCallable { database.articleDataDao().getArticleData() }
    }

    override fun saveArticleListInDb(articleList: List<Article>): Completable {
        return Single.just(database.articleDataDao().insertArticleData(articleList)).toCompletable()
    }

    override fun getArticleDetailFromDb(id: Long): Single<Article> {
        return Single.fromCallable{(database.articleDataDao().getArticleDetails(id))}
    }

    override fun deleteArticlesForPreviousDates(newContentInsertionTime: Long): Completable {
        return Single.just(database.articleDataDao().deleteArticlesForPreviousDates(newContentInsertionTime)).toCompletable()
    }
}