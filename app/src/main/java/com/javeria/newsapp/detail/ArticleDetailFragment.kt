package com.javeria.newsapp.detail

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.javeria.newsapp.R
import com.javeria.newsapp.data.Injection
import com.javeria.newsapp.data.local.db.entity.Article
import kotlinx.android.synthetic.main.article_card_view.img_news
import kotlinx.android.synthetic.main.article_card_view.txt_news_date
import kotlinx.android.synthetic.main.article_card_view.txt_news_src
import kotlinx.android.synthetic.main.article_card_view.txt_news_title
import kotlinx.android.synthetic.main.fragment_article_detail.*

class ArticleDetailFragment(article: Article) : Fragment(),
    ArticleDetailContract.View, View.OnClickListener, View.OnKeyListener {

    override lateinit var presenter: ArticleDetailContract.Presenter
    var mArticle: Article

    init {
        mArticle = article
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_article_detail, container, false)

        if (!::presenter.isInitialized) {
            presenter = ArticleDetailPresenter(
                Injection.provideDataRepository(context!!), this, mArticle
            )
            presenter.getArticleDetailFromDb()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    override fun showArticleDetail() {
        layout_article_detail.visibility = View.VISIBLE
        if (mArticle.urlToImage != null)
            Glide.with(context!!).load(mArticle.urlToImage).into(img_news)
        txt_news_title.setText(mArticle.title)
        txt_article_detail.setText(mArticle.description)
        txt_news_src.setText(mArticle.sourceName)
        txt_news_date.setText(mArticle.publishedAt)
    }

    private fun setOnClickListener() {
        img_back.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stop()
    }

    override fun onClick(v: View?) {
        popFragment()
    }

    override fun popFragment() {
        try {
            if (isAdded) {
                if (fragmentManager != null) {
                    val fragment = fragmentManager!!.findFragmentByTag("ArticleDetailFragment")
                    if (fragment != null) {
                        if (!fragmentManager!!.isStateSaved) {
                            fragmentManager!!.popBackStack()
                        }
                    }
                }
            }
        } catch (ex: Throwable) {
            ex.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener(this)
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
            popFragment()
            return true
        }
        return false
    }
}