package com.iti.azzurra.main_navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
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
import com.skydoves.cloudy.cloudy
import com.skydoves.cloudy.rememberSky
import com.skydoves.cloudy.sky
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
    val sky = rememberSky()

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
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(bottom = 8.dp)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.surfaceContainerHigh, CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainer, CircleShape)
                        .cloudy(sky, radius = 32, cpuBlurEnabled = false)
                        .padding(6.dp)
                ) {
                    BottomNavRoute.entries.forEach { route ->
                        val isSelected = currentRoute?.destination?.hasRoute(route.destination::class) ?: false
                        AzzurraBottomNavigationItem(
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
                            icon = if (isSelected) route.selectedIconId else route.iconId,
                            label = route.titleId,
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        CompositionLocalProvider(
            LocalBottomBarHeight provides innerPadding.calculateBottomPadding(),
            LocalSky provides sky
        ) {
            Box(
               modifier = Modifier
                   .fillMaxSize()
                   .sky(sky)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = innerPadding.calculateTopPadding()),
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
        }
    }
    if (shouldShowMapDialog) {
        MapDialog(
            onDismissRequest = { shouldShowMapDialog = false }
        )
    }
}