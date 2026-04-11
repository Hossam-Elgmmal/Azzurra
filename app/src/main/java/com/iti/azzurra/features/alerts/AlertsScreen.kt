package com.iti.azzurra.features.alerts

import android.Manifest
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iti.azzurra.R
import com.iti.azzurra.common.PermissionsDialog
import com.iti.azzurra.features.alerts.components.WeatherAlertCard
import com.iti.azzurra.features.alerts.components.WeatherAlertDialog
import com.iti.azzurra.main_navigation.LocalBottomBarHeight
import com.iti.azzurra.main_navigation.LocalHazeState
import com.iti.azzurra.ui.theme.AzzurraTheme
import com.iti.azzurra.utils.hasNotificationPermission
import dev.chrisbanes.haze.hazeEffect

@Composable
fun AlertsRoot(
    viewModel: AlertsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AlertsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen(
    state: AlertsState,
    onAction: (AlertsAction) -> Unit,
) {
    val hazeState = LocalHazeState.current
    val bottomBarHeight = LocalBottomBarHeight.current

    val context = LocalContext.current
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.alerts),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .hazeEffect(state = hazeState)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val hasPermission = context.hasNotificationPermission()
                    if (hasPermission) {
                        onAction(AlertsAction.OpenAddAlertDialog(true))
                    } else {
                        onAction(AlertsAction.ShowNotificationDialog(true))
                    }
                },
                modifier = Modifier.padding(bottom = bottomBarHeight)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_alarm),
                    contentDescription = stringResource(R.string.add_alert)
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing.exclude(
            WindowInsets.navigationBars
        )
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(state.allAlerts, key = {it.alertId}) { alert ->
                WeatherAlertCard(
                    alert = alert,
                    onToggleActive = {
                        onAction(AlertsAction.ToggleActivateAlert(alert, it))
                    },
                    onItemDelete = {
                        onAction(AlertsAction.DeleteAlert(alert))
                    },
                    hazeState = hazeState
                )
            }
            item {
                Spacer(
                    modifier = Modifier.height(56.dp)
                )
            }
            item {
                Spacer(
                    modifier = Modifier.height(bottomBarHeight)
                )
            }
        }
    }
    if (state.showAddAlertDialog) {
        WeatherAlertDialog(
            onConfirm = {
                onAction(AlertsAction.AddAlert(it))
            },
            currentUnit = state.settings.temperatureUnit,
            onDismiss = {
                onAction(AlertsAction.OpenAddAlertDialog(false))
            }
        )
    }
    if (state.showNotificationPermissionDialog && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        PermissionsDialog(
            titleId = R.string.notification_permission,
            textId = R.string.please_allow_azzurra_to_send_you_notifications,
            onDismiss = {
                onAction(AlertsAction.ShowNotificationDialog(false))
            },
            onGranted = {
                onAction(AlertsAction.OpenAddAlertDialog(true))
                onAction(AlertsAction.ShowNotificationDialog(false))
            },
            neededPermissions = arrayOf(
                Manifest.permission.POST_NOTIFICATIONS
            ),
            iconId = R.drawable.ic_notification,
        )
    }
}

@Preview
@Composable
private fun AlertsScreenPreview() {
    AzzurraTheme {
        AlertsScreen(
            state = AlertsState(),
            onAction = {}
        )
    }
}