package com.javeria.newsapp.adapter

import com.javeria.newsapp.R
import com.javeria.newsapp.data.local.db.entity.Article
import com.javeria.viewholder.ArticleViewHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class ArticlesListAdapter(val context: Context, var articleList: MutableList<Article>, clickListener: ClickListener) : RecyclerView.Adapter<ArticleViewHolder>() {

    var clickListener: ClickListener

    init {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.article_card_view, parent, false)
        return ArticleViewHolder(context, itemView, clickListener)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articleList[holder.adapterPosition])
    }

    interface ClickListener {
        fun onClick(article: Article)
    }
}