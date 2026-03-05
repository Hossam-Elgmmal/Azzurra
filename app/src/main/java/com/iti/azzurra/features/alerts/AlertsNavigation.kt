package com.iti.azzurra.features.alerts

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


fun NavGraphBuilder.alertsNavigation() {
    composable<AlertsRoute> {
        AlertsRoot()
    }
}