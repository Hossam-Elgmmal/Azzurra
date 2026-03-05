package com.iti.azzurra.features.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


fun NavGraphBuilder.homeNavigation() {
    composable<HomeRoute> {
        HomeRoot()
    }
}