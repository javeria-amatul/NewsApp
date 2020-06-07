package com.javeria.newsapp

import androidx.databinding.ObservableArrayList
import com.javeria.newsapp.adapter.ArticlesListAdapter
import com.javeria.newsapp.data.DataSourceInterface
import com.javeria.newsapp.data.local.db.entity.Article
import com.javeria.newsapp.data.remote.Endpoints
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityPresenter(
    var dataSourceInterface: DataSourceInterface,
    val view: MainActivityContract.View
) : MainActivityContract.Presenter {


    var mDataSourceInterface: DataSourceInterface
    var mMainActivityView: MainActivityContract.View
    var compositeDisposable = CompositeDisposable()

    init {
        mDataSourceInterface = dataSourceInterface
        mMainActivityView = view
    }

    override fun fetchArticleList() {
        compositeDisposable.add(
            dataSourceInterface.getArticleList(
                Endpoints.GET_ARTICLES,
                Const.country,
                Const.api_key,
                false
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res ->
                    if (res != null) {
                        if (res.isNotEmpty()) {
                            view.showArticleList(res)
                        }
                    }
                }, {})
        )
    }

    override fun reloadArticleList(
        articleList: ObservableArrayList<Article>,
        articleListAdapter: ArticlesListAdapter
    ) {
        compositeDisposable.add(
            dataSourceInterface.getArticleList(
                Endpoints.GET_ARTICLES,
                Const.country,
                Const.api_key,
                true
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                        res ->
                    if(res.isNotEmpty()){
                        (articleList).clear()
                        articleList.addAll(res)
                        articleListAdapter.notifyDataSetChanged()
                    }
                },{})
        )
    }

    override fun stop() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}