package com.iti.azzurra.features.settings

import com.iti.azzurra.data.settings.models.UserSettings

data class SettingsState(
    val settings: UserSettings = UserSettings(),
    val showLanguageDialog: Boolean = false,
    val showThemeDialog: Boolean = false,
    val shouldShowLocationPermissionDialog: Boolean = false,
    val cityName: String = ""
)