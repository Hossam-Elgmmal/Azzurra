package com.iti.azzurra.features.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


fun NavGraphBuilder.settingsNavigation(
    showMapDialog: () -> Unit
) {
    composable<SettingsRoute> {
        SettingsRoot(
            showMapDialog = showMapDialog
        )
    }
}