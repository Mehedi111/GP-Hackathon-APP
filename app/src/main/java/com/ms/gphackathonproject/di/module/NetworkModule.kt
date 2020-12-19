package com.gm.lollipop.di.module

import android.content.Context
import com.gm.lollipop.data.storage.LocalStorage
import com.gm.lollipop.utils.gson.AnnotationExclusionStrategy
import com.gm.lollipop.utils.network.interceptor.HostSelectionInterceptor
import com.gm.lollipop.utils.network.interceptor.NetworkConnectionInterceptor
import com.gm.lollipop.utils.network.interceptor.TokenAuthenticatorInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ms.gphackathonproject.BuildConfig
import com.ms.gphackathonproject.Constants
import com.ms.gphackathonproject.data.rest.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Mehedi Hasan on 11/25/2020.
 */
@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun gson(): Gson {
        return GsonBuilder()
            .setExclusionStrategies(AnnotationExclusionStrategy())
            .create()
    }

    @Singleton
    @Provides
    fun cache(context: Context): Cache {
        return Cache(
            File(context.cacheDir, Constants.HTTP_CACHE_DIR),
            Constants.HTTP_CACHE_SIZE
        )
    }

    @Singleton
    @Provides
    @Named("base")
    fun baseOkHttpClient(
        context: Context,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(null)
            .addInterceptor(NetworkConnectionInterceptor(context))
            .addInterceptor(loggingInterceptor)
            .readTimeout(Constants.HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(
                Constants.HTTP_CONNECT_TIMEOUT,
                TimeUnit.SECONDS
            )
            .build()
    }


    @Singleton
    @Provides
    fun okHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor { message: String? ->
            Timber.tag("httpLogging").d(message)
        }
        return logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    @Named("base")
    fun baseRetrofit(@Named("base") okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    @Singleton
    @Provides
    fun baseApiService(
        @Named("base") retrofit: Retrofit
    ): ApiService {
        val apiService: ApiService = retrofit.create(ApiService::class.java)
        return apiService
    }

    @Singleton
    @Provides
    fun hostSelectionInterceptor(): HostSelectionInterceptor {
        return HostSelectionInterceptor()
    }

}