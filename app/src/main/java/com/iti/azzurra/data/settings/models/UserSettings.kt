package com.iti.azzurra.data.settings.models

import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val language: LanguageSetting = LanguageSetting.ENGLISH,
    val theme: ThemeSetting = ThemeSetting.FOLLOW_SYSTEM,
    val locationSource: LocationSource = LocationSource.MAP,
    val temperatureUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
    val windSpeedUnit: WindSpeedUnit = WindSpeedUnit.METER_PER_SEC,
    val savedLatitude: Double = 0.0,
    val savedLongitude: Double = 0.0,
    val weatherCondition: WeatherCondition = WeatherCondition.UNKNOWN
)
