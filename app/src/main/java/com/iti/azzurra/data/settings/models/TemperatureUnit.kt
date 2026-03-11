package com.iti.azzurra.data.settings.models

import com.iti.azzurra.R
import kotlinx.serialization.Serializable

@Serializable
enum class TemperatureUnit{
    CELSIUS,
    FAHRENHEIT,
    KELVIN;

    fun getUnitId(): Int {
        return when (this) {
            CELSIUS -> R.string.celsius
            FAHRENHEIT -> R.string.fahrenheit
            KELVIN -> R.string.kelvin
        }
    }

}