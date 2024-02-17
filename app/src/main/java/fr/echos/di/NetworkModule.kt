package fr.echos.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.echos.articles.data.EchosService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private const val API_KEY_PARAMETER_NAME = "apiKey"
        private const val API_KEY_PARAMETER_VALUE = "1b18650f51454f80a97facb29c252ce5"
        // private const val API_KEY_PARAMETER_VALUE = "240bc8c914ff4e9da08298c48435b25c"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiKeyInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
        val url = chain
            .request()
            .url()
            .newBuilder()
            .addQueryParameter(API_KEY_PARAMETER_NAME, API_KEY_PARAMETER_VALUE)
            .build()
        request.url(url)
        chain.proceed(request.build())
    }

    @Provides
    @Singleton
    fun provideBeerService(retrofit: Retrofit): EchosService {
        return retrofit.create(EchosService::class.java)
    }
}
