package com.iti.azzurra.features.home

import com.iti.azzurra.data.settings.models.UserSettings

sealed interface HomeAction {
    data class ShowLocationPermissionDialog(val needPermission: Boolean): HomeAction
    data class FetchNewData(val settings: UserSettings): HomeAction
    data object GetCurrentLocation : HomeAction
}