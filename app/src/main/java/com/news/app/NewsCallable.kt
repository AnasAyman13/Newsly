package com.news.app


import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {

    @GET("/v2/top-headlines?country=us&category=general&apiKey=d35921bcc5dc4fa09f850620f4f14821&pageSize=30")
    fun getNews(): Call<News>
}