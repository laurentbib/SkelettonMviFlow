package com.biblublab.data.homeFeature

import com.google.gson.annotations.SerializedName

data class ArticleResponse(@SerializedName("articles") val articles : List<NewsResponse>)

data class NewsResponse(@SerializedName("title") val title : String,
                        @SerializedName("description") val description : String?,
                        @SerializedName("url") val url : String?,
                        @SerializedName("urlToImage") val urlToImage : String?,
                        @SerializedName("publishedAt") val publishedAt : String
)