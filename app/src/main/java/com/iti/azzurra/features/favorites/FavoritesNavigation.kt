package com.iti.azzurra.features.favorites

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


fun NavGraphBuilder.favoritesNavigation() {
    composable<FavoritesRoute> {
        FavoritesRoot()
    }
}