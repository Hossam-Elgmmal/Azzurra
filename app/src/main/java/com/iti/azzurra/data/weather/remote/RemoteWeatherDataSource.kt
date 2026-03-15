package com.iti.azzurra.data.weather.remote

import com.iti.azzurra.BuildConfig
import com.iti.azzurra.core.network.WeatherDataError
import com.iti.azzurra.core.network.WeatherResult
import com.iti.azzurra.data.weather.remote.models.daily.DailyForecastResponseDto
import com.iti.azzurra.data.weather.remote.models.geocoding.ReverseGeocodingResponseDto
import com.iti.azzurra.data.weather.remote.models.hourly.HourlyForecastResponseDto
import com.iti.azzurra.data.weather.remote.models.pollution.AirPollutionForecastResponseDto
import com.iti.azzurra.utils.Constants

interface RemoteWeatherDataSource {
    suspend fun getHourlyForecast(
        latitude: Double,
        longitude: Double,
        language: String,
        apiKey: String = BuildConfig.WEATHER_API_KEY,
        units: String = "metric",
        count: Int = 96   // 96h = 4 full days
    ): WeatherResult<HourlyForecastResponseDto, WeatherDataError>

    suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double,
        apiKey: String = BuildConfig.WEATHER_API_KEY,
        units: String = "metric",
        count: Int = Constants.DAILY_FORECAST_ITEM_COUNT,
    ): WeatherResult<DailyForecastResponseDto, WeatherDataError>

    suspend fun getAirPollutionForecast(
        latitude: Double,
        longitude: Double,
        apiKey: String = BuildConfig.WEATHER_API_KEY
    ): WeatherResult<AirPollutionForecastResponseDto, WeatherDataError>

    suspend fun getReverseGeoCode(
        latitude: Double,
        longitude: Double,
        apiKey: String = BuildConfig.WEATHER_API_KEY
    ): WeatherResult<List<ReverseGeocodingResponseDto>, WeatherDataError>
}