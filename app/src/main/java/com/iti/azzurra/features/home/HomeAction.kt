package com.iti.azzurra.features.home

sealed interface HomeAction {
    data class ShowLocationPermissionDialog(val needPermission: Boolean): HomeAction
    data object FetchNewData: HomeAction
    data object GetCurrentLocation : HomeAction
}