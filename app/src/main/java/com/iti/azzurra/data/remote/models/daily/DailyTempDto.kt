package com.iti.azzurra.data.remote.models.daily

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyTempDto(
    @SerialName("morn") val morningTemperature: Double? = null,
    @SerialName("day") val dayTemperature: Double? = null,
    @SerialName("eve") val eveningTemperature: Double? = null,
    @SerialName("night") val nightTemperature: Double? = null,
    @SerialName("min") val minimumTemperature: Double? = null,
    @SerialName("max") val maximumTemperature: Double? = null
)