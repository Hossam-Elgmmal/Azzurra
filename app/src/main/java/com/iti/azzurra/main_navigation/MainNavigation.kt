package com.iti.azzurra.main_navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iti.azzurra.R
import com.iti.azzurra.common.AzzurraSnackbarHost
import com.iti.azzurra.common.ObserveEvent
import com.iti.azzurra.common.SnackbarController
import com.iti.azzurra.features.alerts.alertsNavigation
import com.iti.azzurra.features.favorites.favoritesNavigation
import com.iti.azzurra.features.home.HomeRoute
import com.iti.azzurra.features.home.homeNavigation
import com.iti.azzurra.features.map.MapDialog
import com.iti.azzurra.features.settings.SettingsRoute
import com.iti.azzurra.features.settings.settingsNavigation
import kotlinx.coroutines.launch

@Composable
fun MainNavigation(
    startDestination: Route
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val currentRoute by navController.currentBackStackEntryAsState()
    val canOpenMapDialog = currentRoute?.destination?.hasRoute(SettingsRoute::class)?.not() ?: true
    var shouldShowMapDialog by remember { mutableStateOf(false) }

    ObserveEvent(SnackbarController.events) { snackbarEvent ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            if (snackbarEvent.message.isNotBlank()) {
                val result = snackbarHostState.showSnackbar(
                    message = snackbarEvent.message,
                    actionLabel = snackbarEvent.snackbarAction?.name,
                    withDismissAction = snackbarEvent.snackbarAction != null
                )
                if (result == SnackbarResult.ActionPerformed) {
                    snackbarEvent.snackbarAction?.action?.invoke()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { AzzurraSnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (canOpenMapDialog) {
                FloatingActionButton(
                    onClick = { shouldShowMapDialog = true },
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_map),
                        contentDescription = stringResource(R.string.open_map_dialog)
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar {
                BottomNavRoute.entries.forEach { route ->
                    val isSelected = currentRoute?.destination?.hasRoute(route.destination::class) ?: false
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(route.destination) {
                                popUpTo(HomeRoute::class) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            val icon = if (isSelected) route.selectedIconId else route.iconId
                            Icon(
                                imageVector = ImageVector.vectorResource(id = icon),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(route.titleId)
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(animationSpec = tween(350)) },
            exitTransition = { fadeOut(animationSpec = tween(350)) },
            popEnterTransition = { fadeIn(animationSpec = tween(350)) },
            popExitTransition = { fadeOut(animationSpec = tween(350)) },
        ) {
            homeNavigation()
            favoritesNavigation()
            alertsNavigation()
            settingsNavigation()
        }
    }
    if (shouldShowMapDialog) {
        MapDialog(
            onDismissRequest = { shouldShowMapDialog = false }
        )
    }
}