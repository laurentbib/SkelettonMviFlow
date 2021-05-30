package com.biblublab.skelettonmviflow.common.di

import androidx.room.Room
import com.biblublab.data.common.db.SkeletonDataBase
import com.biblublab.data.common.remote.RestApi
import com.biblublab.data.homeFeature.HomeConverter
import com.biblublab.data.homeFeature.HomeRepositoryImpl
import com.biblublab.domain.common.usecase.AppCoroutineDispatchers
import com.biblublab.domain.homeFeature.HomeRepository
import com.biblublab.domain.homeFeature.HomeUseCase
import com.biblublab.skelettonmviflow.homeFeature.HomeViewModel
import com.facebook.shimmer.BuildConfig
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dbModule = module {
    single { Room.databaseBuilder(androidApplication(), SkeletonDataBase::class.java, SkeletonDataBase.DB_NAME).build()}
    single { get<SkeletonDataBase>().homeDao() }
}

val networkModule = module{
single { provideLoggingInterceptor() }
single { providesOkHttpClientBuilder(get()) }
single { providesRetrofit(get()) }
factory { provideRetrofitApi(get()) }
}

val dispatchersModule = module {
    single {
        AppCoroutineDispatchers(
            io = Dispatchers.IO,
            computation = Dispatchers.Default,
            main = Dispatchers.Main
        )
    }
}

val useCaseModule = module{
    factory { HomeUseCase(get(), get()) }
}

val homeModule = module{
    single { HomeConverter() }
    single<HomeRepository> { HomeRepositoryImpl(get(), get(), get()) }
    viewModel { HomeViewModel(get()) }
}

private fun provideLoggingInterceptor() : HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }


private fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()


private fun provideRetrofitApi(retrofit: Retrofit) : RestApi =
    retrofit.create(RestApi::class.java)

private fun providesOkHttpClientBuilder(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient.Builder()
        .apply {
            loggingInterceptor.also {
                addInterceptor(it)
            }
        }.build()

