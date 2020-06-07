package com.javeria.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import com.javeria.newsapp.adapter.ArticlesListAdapter
import com.javeria.newsapp.data.Injection
import com.javeria.newsapp.data.local.db.entity.Article
import com.javeria.newsapp.detail.ArticleDetailFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    override lateinit var presenter: MainActivityContract.Presenter

    var mArticleList = ObservableArrayList<Article>()
    lateinit var mArticleListAdapter: ArticlesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!::presenter.isInitialized) {
            presenter = MainActivityPresenter(
                Injection.provideDataRepository(this),
                this
            )
        }
        rv_articles.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        presenter.fetchArticleList()

    }

    override fun showArticleList(articleList: List<Article>) {
        if (mArticleList.isEmpty()) {
            var listArticle = mArticleList.toMutableList()
            listArticle = articleList.toMutableList()
            mArticleListAdapter =
                ArticlesListAdapter(this, listArticle, object : ArticlesListAdapter.ClickListener {
                    override fun onClick(article: Article) {
                        val articleDetailFragment = ArticleDetailFragment(article)
                        (supportFragmentManager.beginTransaction().replace(
                            R.id.mainActivityLyt,
                            articleDetailFragment,
                            "ArticleDetailFragment"
                        )).addToBackStack("ArticleDetailFragment").commit()
                    }
                })
            rv_articles.adapter = mArticleListAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!::presenter.isInitialized) {
            presenter.stop()
        }
    }
}