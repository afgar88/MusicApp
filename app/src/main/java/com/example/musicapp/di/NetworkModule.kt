package com.example.musicapp.di

import com.example.musicapp.rest.MusicApi
import com.example.musicapp.rest.MusicRepository
import com.example.musicapp.rest.MusicRepositoryImpl
import com.example.musicapp.rest.RequestInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.withTimeout
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    fun providesGson()=Gson()

    @Provides
    fun providesLoginRequestInterceptor()=HttpLoggingInterceptor().apply {
        level=HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun providesRequestInterceptor()=RequestInterceptor()



    @Provides
    fun providesOkHttpClient(requestInterceptor:RequestInterceptor, loginInterceptor:HttpLoggingInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(loginInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()

    }
    @Provides
    @Singleton
    @Named("musicApi")
    fun providesRetrofitService(okHttpClient: OkHttpClient,gson:Gson):MusicApi{
        return Retrofit.Builder()
            .baseUrl(MusicApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MusicApi::class.java)

    }
    @Provides
    fun providesMusicRepository(musicApi:MusicApi):MusicRepository{
        return MusicRepositoryImpl(musicApi)
    }
}