package com.iti.azzurra.data.weather.local.models.current_location


data class CurrentWeatherUi(
    val locationId: String = "",
    val cityCountryText: String = "",
    val dateText: String = "",
    val timeText: String = "",
    val iconCode: String = "",
    val conditionTitle: String = "",
    val conditionDescription: String = "",
    val temperatureText: String = "",
    val feelsLikeText: String = "",
    val minTempText: String = "",
    val maxTempText: String = "",
    val humidityText: String = "",
    val pressureText: String = "",
    val visibilityText: String = "",
    val cloudCoverText: String = "",
    val windSpeedText: String = "",
    val windDirectionText: String = "",
    val windGustText: String = "",
    val rainVolumeText: String = "",
    val snowVolumeText: String = "",
    val sunriseText: String = "",
    val sunsetText: String = "",
    val conditionId: Int = 0,
)