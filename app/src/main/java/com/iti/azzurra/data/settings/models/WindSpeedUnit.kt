package com.iti.azzurra.data.settings.models

import com.iti.azzurra.R
import kotlinx.serialization.Serializable

@Serializable
enum class WindSpeedUnit{
    METER_PER_SEC,
    KILOMETER_PER_HOUR,
    MILES_PER_HOUR;

    fun getUnitId(): Int {
        return when (this) {
            METER_PER_SEC -> R.string.unit_m_per_s
            KILOMETER_PER_HOUR -> R.string.unit_km_per_h
            MILES_PER_HOUR -> R.string.unit_mph
        }
    }
}