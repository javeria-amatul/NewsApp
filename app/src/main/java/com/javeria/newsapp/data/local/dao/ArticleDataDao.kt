package com.javeria.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javeria.newsapp.data.local.db.entity.Article

@Dao
interface ArticleDataDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticleData(articleList: List<Article>)

    @Query("SELECT * FROM articleList ")
    fun getArticleData() : List<Article>

    @Query("SELECT * FROM articleList WHERE id = :id LIMIT 1")
    fun getArticleDetails(id: Long) : Article

    @Query("DELETE FROM articleList WHERE articleInsertionTime <:newContentInsertionTime ")
    fun deleteArticlesForPreviousDates(newContentInsertionTime: Long)
}