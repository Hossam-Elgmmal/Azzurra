package com.iti.azzurra.data.weather.remote.models.hourly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyCloudsDto(
    @SerialName("all") val cloudCoveragePercentage: Int? = null
)