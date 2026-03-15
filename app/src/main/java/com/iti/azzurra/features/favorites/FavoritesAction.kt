package com.iti.azzurra.features.favorites

import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity

sealed interface FavoritesAction {
    data class ToggleSelectedLocation(val location: FavoriteLocationEntity?) : FavoritesAction
    data class DeleteFavoriteLocation(val location: FavoriteLocationEntity) : FavoritesAction
}