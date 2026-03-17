package com.iti.azzurra.data.alert.local.models

import com.iti.azzurra.R


enum class AlertRequirement {
    TEMPERATURE,
    SNOW,
    RAIN;

    fun getLabelId(): Int {
        return when (this) {
            TEMPERATURE -> R.string.temperature
            SNOW -> R.string.snow
            RAIN -> R.string.rain
        }
    }
}