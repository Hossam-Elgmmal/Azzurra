package com.iti.azzurra.features.home

import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionUi
import com.iti.azzurra.data.weather.local.models.current_location.CurrentWeatherUi
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastUi

data class HomeState(
    val settings: UserSettings = UserSettings(),
    val currentWeather: CurrentWeatherUi? = null,
    val hourlyForecast: List<HourlyForecastUi> = emptyList(),
    val airPollution: List<AirPollutionUi> = emptyList(),
    val checkForPermission: Boolean = true,
    val showPermissionDialog: Boolean = false,
    val cityName: String = "",
    val currentLocationId: String = "",
)