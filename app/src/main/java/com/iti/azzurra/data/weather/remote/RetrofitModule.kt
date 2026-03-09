package com.iti.azzurra.data.weather.remote

import android.content.Context
import com.iti.azzurra.BuildConfig
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val CACHE_SIZE = 10 * 1024 * 1024L // 10MB
    private const val TIMEOUT_SECONDS = 40L

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideOkHttpCallFactory(
        @ApplicationContext context: Context
    ): Call.Factory =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        if (BuildConfig.DEBUG) {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    }
            )
            .cache(Cache(context.cacheDir, CACHE_SIZE))
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        json: Json,
        okHttpFactory: Lazy<Call.Factory>
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .callFactory { okHttpFactory.get().newCall(it) }
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .build()

    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)
}