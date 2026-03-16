package com.iti.azzurra.data.weather.remote.models.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CloudCoverageDto(
    @SerialName("all") val cloudCoveragePercentage: Int? = null
)