package com.biblublab.data.common.remote

import com.biblublab.data.homeFeature.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET

interface RestApi {

    @GET("top-headlines?country=fr&apiKey=67d7cfa5dba943539bb5b613b636eebc")
    suspend fun fetchNews() : Response<ArticleResponse>
}