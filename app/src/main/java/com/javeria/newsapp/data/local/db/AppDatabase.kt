package com.javeria.newsapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.javeria.newsapp.Const
import com.javeria.newsapp.Const.DATABASE_VERSION
import com.javeria.newsapp.data.local.converter.SourceConverter
import com.javeria.newsapp.data.local.dao.ArticleDataDao
import com.javeria.newsapp.data.local.db.entity.Article

@Database(
    entities = arrayOf(
        Article::class
    ), version = DATABASE_VERSION, exportSchema = true
)
@TypeConverters(
    SourceConverter::class
)
abstract class AppDatabase : RoomDatabase(){

    abstract fun articleDataDao(): ArticleDataDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        private val lock = Any()


        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, Const.DB_NAME
                    ).addMigrations().build()
                }
                return INSTANCE!!
            }
        }
    }

}