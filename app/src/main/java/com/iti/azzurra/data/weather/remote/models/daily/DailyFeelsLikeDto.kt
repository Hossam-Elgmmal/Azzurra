package com.iti.azzurra.data.weather.remote.models.daily

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyFeelsLikeDto(
    @SerialName("morn") val morningFeelsLike: Double? = null,
    @SerialName("day") val dayFeelsLike: Double? = null,
    @SerialName("eve") val eveningFeelsLike: Double? = null,
    @SerialName("night") val nightFeelsLike: Double? = null
)