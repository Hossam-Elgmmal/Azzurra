package com.iti.azzurra.data.settings.models

import kotlinx.serialization.Serializable

@Serializable
enum class TemperatureUnit{
    CELSIUS,
    FAHRENHEIT,
    KELVIN
}