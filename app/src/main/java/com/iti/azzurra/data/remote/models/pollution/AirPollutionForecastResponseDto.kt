package com.iti.azzurra.data.remote.models.pollution

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirPollutionForecastResponseDto(
    @SerialName("coord") val coordinates: AirPollutionCoordDto? = null,
    @SerialName("list") val pollutionItems: List<AirPollutionItemDto>? = null
)