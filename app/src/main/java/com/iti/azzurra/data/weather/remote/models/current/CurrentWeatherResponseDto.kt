package com.iti.azzurra.data.weather.remote.models.current

import com.iti.azzurra.data.weather.remote.models.common.AtmosphereMainDto
import com.iti.azzurra.data.weather.remote.models.common.CloudCoverageDto
import com.iti.azzurra.data.weather.remote.models.common.CoordinatesDto
import com.iti.azzurra.data.weather.remote.models.common.WeatherConditionDto
import com.iti.azzurra.data.weather.remote.models.common.WindDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponseDto(
    @SerialName("coord") val coordinates: CoordinatesDto? = null,
    @SerialName("weather") val weatherConditions: List<WeatherConditionDto>? = null,
    @SerialName("base") val dataSource: String? = null,
    @SerialName("main") val atmosphericReadings: AtmosphereMainDto? = null,
    @SerialName("visibility") val visibilityMeters: Int? = null,
    @SerialName("wind") val windConditions: WindDto? = null,
    @SerialName("rain") val rainAccumulation: CurrentWeatherAccumulationDto? = null,
    @SerialName("snow") val snowAccumulation: CurrentWeatherAccumulationDto? = null,
    @SerialName("clouds") val cloudCoverage: CloudCoverageDto? = null,
    @SerialName("dt") val observationTimestamp: Long? = null,
    @SerialName("sys") val systemMetadata: SystemMetadataDto? = null,
    @SerialName("timezone") val timezoneOffsetSeconds: Int? = null,
    @SerialName("id") val cityId: Long? = null,
    @SerialName("name") val cityName: String? = null,
    @SerialName("cod") val httpStatusCode: Int? = null
)