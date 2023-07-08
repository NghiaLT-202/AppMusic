package com.example.appmusic.di

import com.example.tfmmusic.common.Constant
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient?
    ): Retrofit {
        return Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun youtubeAPI(
        retrofit: Retrofit
    ): YoutubeApi {
        return retrofit.create(YoutubeApi::class.java)
    }

    @Provides
    @Singleton
    fun providesOkHttpClientAppVersion(): OkHttpClient {
        val client: OkHttpClient.Builder = Builder()
            .connectTimeout(Constant.CONNECT_S, TimeUnit.SECONDS)
            .writeTimeout(Constant.WRITE_S, TimeUnit.SECONDS)
            .readTimeout(Constant.READ_S, TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        client.addNetworkInterceptor(interceptor)
        return client.build()
    }
}