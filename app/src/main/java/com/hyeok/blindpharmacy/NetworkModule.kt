package com.hyeok.blindpharmacy

import com.hyeok.blindpharmacy.config.PictureAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val loggingInterceptor = HttpLoggingInterceptor()
    private const val DEBUG = true

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor.apply {
                if (DEBUG){
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun providePictureAPI(client: OkHttpClient,
    ): PictureAPI {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(PictureAPI.TEXT_API_BASE)
            .build().create(PictureAPI::class.java)
    }

}