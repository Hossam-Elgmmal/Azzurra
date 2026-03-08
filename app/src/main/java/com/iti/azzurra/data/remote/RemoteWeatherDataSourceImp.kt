package com.iti.azzurra.data.remote

import com.iti.azzurra.core.network.WeatherDataError
import com.iti.azzurra.core.network.WeatherError
import com.iti.azzurra.core.network.WeatherResult
import com.iti.azzurra.core.network.safeApiCall
import com.iti.azzurra.data.remote.models.daily.DailyForecastResponseDto
import com.iti.azzurra.data.remote.models.hourly.HourlyForecastResponseDto
import com.iti.azzurra.data.remote.models.pollution.AirPollutionForecastResponseDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteWeatherDataSourceImp @Inject constructor(
    private val weatherService: WeatherService
): RemoteWeatherDataSource {
    override suspend fun getHourlyForecast(
        latitude: Double,
        longitude: Double,
        language: String,
        apiKey: String,
        units: String,
        count: Int
    ): WeatherResult<HourlyForecastResponseDto, WeatherDataError> {
        return safeApiCall{
            weatherService.getHourlyForecast(
                latitude = latitude,
                longitude = longitude,
                language = language,
                apiKey = apiKey,
                units = units,
                count = count
            )
        }
    }

    override suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double,
        language: String,
        apiKey: String,
        units: String,
        count: Int
    ): WeatherResult<DailyForecastResponseDto, WeatherDataError> {
        return safeApiCall{
            weatherService.getDailyForecast(
                latitude = latitude,
                longitude = longitude,
                language = language,
                apiKey = apiKey,
                units = units,
                count = count,
            )
        }
    }

    override suspend fun getAirPollutionForecast(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): WeatherResult<AirPollutionForecastResponseDto, WeatherDataError> {
        return safeApiCall{
            weatherService.getAirPollutionForecast(
                latitude = latitude,
                longitude = longitude,
                apiKey = apiKey,
            )
        }
    }
}