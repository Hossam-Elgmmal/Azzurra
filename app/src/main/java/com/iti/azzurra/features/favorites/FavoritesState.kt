package com.iti.azzurra.features.favorites

import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.weather.local.models.favorites.DailyForecast
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity

data class FavoritesState(
    val settings: UserSettings = UserSettings(),
    val isLoading: Boolean = false,
    val selectedLocation: FavoriteLocationEntity? = null,
    val favoriteLocations: List<FavoriteLocationEntity> = emptyList(),
    val selectedWeatherList: List<DailyForecast> = emptyList()
)