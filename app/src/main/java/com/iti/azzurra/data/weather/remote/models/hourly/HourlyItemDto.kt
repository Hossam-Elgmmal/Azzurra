package com.iti.azzurra.data.weather.remote.models.hourly

import com.iti.azzurra.data.weather.remote.models.common.AtmosphereMainDto
import com.iti.azzurra.data.weather.remote.models.common.CloudCoverageDto
import com.iti.azzurra.data.weather.remote.models.common.WindDto
import com.iti.azzurra.data.weather.remote.models.common.WeatherConditionDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyItemDto(
    @SerialName("dt") val timestamp: Long? = null,
    @SerialName("main") val temperatures: AtmosphereMainDto? = null,
    @SerialName("weather") val weatherConditions: List<WeatherConditionDto>? = null,
    @SerialName("clouds") val clouds: CloudCoverageDto? = null,
    @SerialName("wind") val wind: WindDto? = null,
    @SerialName("rain") val rain: HourlyPrecipitationDto? = null,
    @SerialName("snow") val snow: HourlyPrecipitationDto? = null,
    @SerialName("visibility") val visibilityMeters: Int? = null,
    @SerialName("pop") val precipitationChance: Double? = null,   // 0.0–1.0
    @SerialName("dt_txt") val timestampText: String? = null    // "2026-01-01 14:00:00"
)