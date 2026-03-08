package com.iti.azzurra.data.remote.models.pollution

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirPollutionItemDto(
    @SerialName("dt") val timestamp: Long? = null,
    @SerialName("main") val index: AirQualityIndexDto? = null,
    @SerialName("components") val components: AirComponentsDto? = null
)