package com.javeria.newsapp.data.remote

import androidx.annotation.VisibleForTesting
import com.javeria.newsapp.data.local.db.entity.News
import io.reactivex.Single

class AppApiHelper(val apiService: ApiService) : ApiHelper {

    private val TAG = javaClass.canonicalName

    companion object {
        private var INSTANCE: AppApiHelper? = null

        @JvmStatic
        fun getInstance(apiService: ApiService): AppApiHelper {
            if (INSTANCE == null) {
                synchronized(AppApiHelper::javaClass) {
                    INSTANCE = AppApiHelper(apiService)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

    override fun getArticlesListFromServer(url: String, country: String, apiKey: String): Single<News> {
        return apiService.getArticlesList(url, country, apiKey)
    }
}