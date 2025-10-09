package com.news.app

data class News(
    val articles: ArrayList<Article>
)
data class Article(
    val title: String,
    val url: String,
    val urlToImage: String
)
