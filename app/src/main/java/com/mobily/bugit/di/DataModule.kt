package com.mobily.bugit.di

import com.mobily.bugit.data.addBug.remote.AddBugApiService
import com.mobily.bugit.data.getBugs.remote.GetBugsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideGetBugsApiService(retrofit: Retrofit): GetBugsApiService = retrofit.create(
        GetBugsApiService::class.java)

    @Provides
    fun provideAddBugsApiService(retrofit: Retrofit): AddBugApiService = retrofit.create(
        AddBugApiService::class.java)

    @Provides
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        client: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "https://sheets.googleapis.com/"
}