package com.iti.azzurra.features.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


fun NavGraphBuilder.settingsNavigation() {
    composable<SettingsRoute> {
        SettingsRoot()
    }
}