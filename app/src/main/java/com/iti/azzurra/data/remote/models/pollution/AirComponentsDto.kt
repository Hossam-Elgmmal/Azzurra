package com.iti.azzurra.data.remote.models.pollution

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirComponentsDto(
    @SerialName("co") val carbonMonoxide: Double? = null,
    @SerialName("no") val nitrogenMonoxide: Double? = null,
    @SerialName("no2") val nitrogenDioxide: Double? = null,
    @SerialName("o3") val ozone: Double? = null,
    @SerialName("so2") val sulphurDioxide: Double? = null,
    @SerialName("pm2_5") val particulateMatter2AndHalf: Double? = null,   // 2.5 μg/m³ — fine particles
    @SerialName("pm10") val particulateMatter10: Double? = null,   // 10 μg/m³ — coarse particles
    @SerialName("nh3") val ammonia: Double? = null
)