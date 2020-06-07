package com.javeria.newsapp.detail

import com.javeria.newsapp.data.DataSourceInterface
import com.javeria.newsapp.data.local.db.entity.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ArticleDetailPresenter(
    var dataSourceInterface: DataSourceInterface,
    val view: ArticleDetailContract.View,
    article: Article
) : ArticleDetailContract.Presenter {

    private val TAG = ArticleDetailPresenter::class.java.canonicalName
    var compositeDisposable = CompositeDisposable()

    var mArticle: Article

    init {
        mArticle = article
    }

    override fun getArticleDetailFromDb() {
        compositeDisposable.add(dataSourceInterface.getArticleDetails(mArticle.id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe({
                    res ->
                if(res != null){
                    view.showArticleDetail()
                }
            }
                ,
                {}
            ))

    }

    override fun stop() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}