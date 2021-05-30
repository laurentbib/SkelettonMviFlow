package com.biblublab.data.homeFeature

import com.biblublab.data.common.EMPTY_STRING
import com.biblublab.domain.homeFeature.entities.HomeNews

class HomeConverter {
    fun toNewsList(articleResponse: ArticleResponse) : List<HomeDataBaseObject> = articleResponse.articles.map { toHomeDataBaseObject(it) }

    private fun toHomeDataBaseObject(newsResponse: NewsResponse) : HomeDataBaseObject {
        return with(newsResponse){
            HomeDataBaseObject(title = title,
                description = description ?: EMPTY_STRING,
                url = url ?: EMPTY_STRING,
                urlImage = urlToImage ?: EMPTY_STRING,
                timeStamp = publishedAt
            )
        }
    }

    fun toNewsFromDBList(allNews : List<HomeDataBaseObject>) : List<HomeNews> = allNews.map { toHomeNewsFromDb(it) }

    private fun toHomeNewsFromDb(newsDataBaseObject : HomeDataBaseObject) : HomeNews {
        return with(newsDataBaseObject){
            HomeNews(title, description, url, urlImage, timeStamp )
        }
    }
}