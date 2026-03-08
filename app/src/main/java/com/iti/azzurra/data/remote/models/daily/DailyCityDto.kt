package com.iti.azzurra.data.remote.models.daily

import com.iti.azzurra.data.remote.models.common.CoordinatesDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyCityDto(
    @SerialName("id") val cityId: Int? = null,
    @SerialName("name") val cityName: String? = null,
    @SerialName("coord") val coordinates: CoordinatesDto? = null,
    @SerialName("country") val countryCode: String? = null,
    @SerialName("population") val population: Int? = null,
    @SerialName("timezone") val timezoneOffset: Int? = null
)