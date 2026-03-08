package com.iti.azzurra.data.remote.models.pollution

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirQualityIndexDto(
    @SerialName("aqi") val airQualityIndex: Int? = null
    // 1 = Good
    // 2 = Fair
    // 3 = Moderate
    // 4 = Poor
    // 5 = Very Poor
)