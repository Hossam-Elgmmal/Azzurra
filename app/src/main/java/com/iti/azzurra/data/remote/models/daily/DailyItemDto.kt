package com.iti.azzurra.data.remote.models.daily

import com.iti.azzurra.data.remote.models.common.WeatherConditionDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DailyItemDto(
    @SerialName("dt") val timestamp: Long? = null,
    @SerialName("sunrise") val sunriseTimestamp: Long? = null,
    @SerialName("sunset") val sunsetTimestamp: Long? = null,
    @SerialName("temp") val temperature: DailyTempDto? = null,
    @SerialName("feels_like") val feelsLike: DailyFeelsLikeDto? = null,
    @SerialName("pressure") val atmosphericPressure: Int? = null,
    @SerialName("humidity") val humidityPercentage: Int? = null,
    @SerialName("weather") val weatherConditions: List<WeatherConditionDto>? = null,
    @SerialName("speed") val windSpeed: Double? = null,
    @SerialName("deg") val windDirectionDegrees: Int? = null,
    @SerialName("gust") val windGustSpeed: Double? = null,
    @SerialName("clouds") val cloudCoveragePercentage: Int? = null,
    @SerialName("pop") val precipitationProbability: Double? = null,
    @SerialName("rain") val rainVolumeMm: Double? = null,
    @SerialName("snow") val snowVolumeMm: Double? = null
)