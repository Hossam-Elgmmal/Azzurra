package com.iti.azzurra.data.weather.remote.models.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherConditionDto(
    @SerialName("id") val conditionId: Int? = null,
    @SerialName("main") val conditionGroup: String? = null,       // e.g. "Rain", "Clear"
    @SerialName("description") val conditionDescription: String? = null, // e.g. "light rain"
    @SerialName("icon") val iconCode: String? = null              // e.g. "01d"
)