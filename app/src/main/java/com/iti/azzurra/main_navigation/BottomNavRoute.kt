package com.iti.azzurra.main_navigation

import com.iti.azzurra.R
import com.iti.azzurra.features.alerts.AlertsRoute
import com.iti.azzurra.features.favorites.FavoritesRoute
import com.iti.azzurra.features.home.HomeRoute
import com.iti.azzurra.features.settings.SettingsRoute

enum class BottomNavRoute(
    val titleId: Int,
    val iconId: Int,
    val selectedIconId: Int,
    val destination: Route,
) {
    HOME(
        titleId = R.string.home,
        iconId = R.drawable.ic_home,
        selectedIconId = R.drawable.ic_home_filled,
        destination = HomeRoute
    ),
    FAVORITES(
        titleId = R.string.favorites,
        iconId = R.drawable.ic_favorites,
        selectedIconId = R.drawable.ic_favorites_filled,
        destination = FavoritesRoute
    ),
    ALERTS(
        titleId = R.string.alerts,
        iconId = R.drawable.ic_alert,
        selectedIconId = R.drawable.ic_alert_filled,
        destination = AlertsRoute
    ),
    SETTINGS(
        titleId = R.string.settings,
        iconId = R.drawable.ic_settings,
        selectedIconId = R.drawable.ic_settings_filled,
        destination = SettingsRoute
    )
}