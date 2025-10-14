package com.news.app


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCallable {

    @GET("/v2/top-headlines")
    fun getNews(
        @Query("country") country: String ,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = "8819d1d4ffe8450ea42c9c7d28da1a4a",
        @Query("pageSize") pageSize: Int = 30
    ): Call<News>
}