package com.iti.azzurra.features.settings

import com.iti.azzurra.data.settings.models.LocationSource
import com.iti.azzurra.data.settings.models.TemperatureUnit
import com.iti.azzurra.data.settings.models.ThemeSetting
import com.iti.azzurra.data.settings.models.WindSpeedUnit

sealed interface SettingsAction {
    data class UpdateTheme(val theme: ThemeSetting) : SettingsAction
    data class UpdateLocationSource(val locationSource: LocationSource) : SettingsAction
    data class UpdateTemperatureUnit(val temperatureUnit: TemperatureUnit) : SettingsAction
    data class UpdateWindSpeedUnit(val windSpeedUnit: WindSpeedUnit) : SettingsAction
    data class LanguageDialogToggle(val open: Boolean) : SettingsAction
    data class ThemeDialogToggle(val open: Boolean) : SettingsAction
    data object GetLocationPermission: SettingsAction
    data object CancelGettingLocationPermission: SettingsAction
    data object GetCurrentLocation : SettingsAction
}