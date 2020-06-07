package com.javeria.newsapp

import androidx.databinding.ObservableArrayList
import com.javeria.newsapp.adapter.ArticlesListAdapter
import com.javeria.newsapp.data.local.db.entity.Article

interface MainActivityContract {


    interface View{

        fun showArticleList(articleList : List<Article>)
        var presenter: Presenter
    }

    interface Presenter {

        fun fetchArticleList()

        fun reloadArticleList(articleList: ObservableArrayList<Article>, articleListAdapter: ArticlesListAdapter)

        fun stop()
    }

}