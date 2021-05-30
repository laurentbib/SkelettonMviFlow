package com.biblublab.data.homeFeature

import com.biblublab.data.common.UTC_DATE_FORMAT
import com.biblublab.data.common.remote.RestApi
import com.biblublab.domain.common.usecase.Either
import com.biblublab.domain.common.usecase.Failure
import com.biblublab.domain.homeFeature.HomeRepository
import com.biblublab.domain.homeFeature.entities.HomeNews
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class HomeRepositoryImpl(private val restApi: RestApi, private val homeConverter: HomeConverter, private val homeDao: HomeDao) : HomeRepository {

    override fun fetchNews(): Flow<Either<Failure, List<HomeNews>>> {
        return fetchNewsFromRemote().onEach {
            if (it is Either.Successful) {
                homeConverter.toNewsList(it.success).forEach { newsDataBaseObject ->
                    homeDao.insert(newsDataBaseObject)
                }
            }
        }.map {
            val newsFromDb = getNewsFromDbSortByPublishedDate()
            when (it) {
                is Either.Failure -> if (newsFromDb.isNotEmpty()) Either.Successful(newsFromDb) else Either.Failure(
                    it.fail
                )
                is Either.Successful -> Either.Successful(newsFromDb)
            }
        }
    }

    private fun fetchNewsFromRemote(): Flow<Either<Failure, ArticleResponse>> = flow {
        while (true) {
            emit(safeApiCall { restApi.fetchNews() })
            delay(20000)
        }
    }

    private suspend fun getNewsFromDbSortByPublishedDate() = homeConverter.toNewsFromDBList(homeDao.getAllNews()).sortedByDescending { news-> SimpleDateFormat(UTC_DATE_FORMAT, Locale.getDefault()).parse(news.formattedDate) }

    private suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Either<Failure, T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful && response.body() != null) {
                response.body()?.let {
                    Either.Successful(it)
                } ?: Either.Failure(Failure.NO_DATA)
            } else {
                Either.Failure(Failure.NETWORK_ERROR)
            }
        } catch (e: Exception) {
            Either.Failure(Failure.UNKNOWN_ERROR)
        }
    }
}
