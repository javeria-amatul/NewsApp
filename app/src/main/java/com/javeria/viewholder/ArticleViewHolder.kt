package com.javeria.viewholder

import com.javeria.newsapp.R
import com.javeria.newsapp.data.local.db.entity.Article


import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.javeria.newsapp.adapter.ArticlesListAdapter

class ArticleViewHolder(
    val context: Context,
    view: View,
    val clickListener: ArticlesListAdapter.ClickListener
) : RecyclerView.ViewHolder(view), View.OnClickListener {

    var ivArticle: ImageView
    var tvArticleTitle: TextView
    var tvArticleSrc: TextView
    var tvArticleDate: TextView
    lateinit var mArticle: Article

    init {
        ivArticle = view.findViewById(R.id.img_news)
        tvArticleTitle = view.findViewById(R.id.txt_news_title)
        tvArticleSrc = view.findViewById(R.id.txt_news_src)
        tvArticleDate = view.findViewById(R.id.txt_news_date)
        ivArticle.setOnClickListener(this)
    }

    fun bind(article: Article) {
        this.mArticle = article
        tvArticleTitle.setText(mArticle.title)
        tvArticleSrc.setText(mArticle.sourceName)
        tvArticleDate.setText(mArticle.publishedAt)

        Glide.with(context).load(mArticle.urlToImage).into(ivArticle)
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.img_news ->
            {
                clickListener.onClick(mArticle)
            }
        }
    }
}