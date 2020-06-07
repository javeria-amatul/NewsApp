package com.javeria.newsapp.data.parser

import com.javeria.newsapp.data.local.db.entity.Article
import com.javeria.newsapp.data.local.db.entity.News

interface IParser {
    fun parseNewsJSON( newsResponseData: News): List<Article>
}