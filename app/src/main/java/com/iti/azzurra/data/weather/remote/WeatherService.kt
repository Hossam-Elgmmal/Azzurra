package com.iti.azzurra.data.weather.remote

import com.iti.azzurra.BuildConfig
import com.iti.azzurra.data.weather.remote.models.daily.DailyForecastResponseDto
import com.iti.azzurra.data.weather.remote.models.hourly.HourlyForecastResponseDto
import com.iti.azzurra.data.weather.remote.models.pollution.AirPollutionForecastResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/forecast/hourly")
    suspend fun getHourlyForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String = "en",
        @Query("appid") apiKey: String = BuildConfig.WEATHER_API_KEY,
        @Query("units") units: String = "metric",
        @Query("cnt") count: Int = 96   // 96h = 4 full days
    ): Response<HourlyForecastResponseDto>

    @GET("data/2.5/forecast/daily")
    suspend fun getDailyForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String = "en",
        @Query("appid") apiKey: String = BuildConfig.WEATHER_API_KEY,
        @Query("units") units: String = "metric",
        @Query("cnt") count: Int = 16
    ): Response<DailyForecastResponseDto>

    @GET("data/2.5/air_pollution/forecast")
    suspend fun getAirPollutionForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = BuildConfig.WEATHER_API_KEY
    ): Response<AirPollutionForecastResponseDto>
}