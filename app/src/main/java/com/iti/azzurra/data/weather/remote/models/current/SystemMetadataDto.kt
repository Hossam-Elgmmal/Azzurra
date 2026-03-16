package com.iti.azzurra.data.weather.remote.models.current

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SystemMetadataDto(
    @SerialName("type") val calculationType: Int? = null,
    @SerialName("id") val internalStationId: Int? = null,
    @SerialName("country") val countryCode: String? = null,
    @SerialName("sunrise") val sunriseTimestamp: Long? = null,
    @SerialName("sunset") val sunsetTimestamp: Long? = null
)