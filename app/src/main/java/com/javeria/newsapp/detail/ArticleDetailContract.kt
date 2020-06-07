package com.javeria.newsapp.detail

interface ArticleDetailContract {

    interface View {

        fun showArticleDetail()
        var presenter: Presenter
        fun popFragment()

    }

    interface Presenter {
        fun getArticleDetailFromDb()

        fun stop()
    }
}