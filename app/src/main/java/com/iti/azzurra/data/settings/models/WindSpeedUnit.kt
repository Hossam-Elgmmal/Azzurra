package com.iti.azzurra.data.settings.models

import com.iti.azzurra.R
import kotlinx.serialization.Serializable

@Serializable
enum class WindSpeedUnit{
    METER_PER_SEC,
    MILES_PER_HOUR;

    fun getUnitId(): Int {
        return when (this) {
            METER_PER_SEC -> R.string.m_s
            MILES_PER_HOUR -> R.string.mph
        }
    }
}