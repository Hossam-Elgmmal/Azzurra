package com.iti.azzurra.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iti.azzurra.R
import com.iti.azzurra.features.map.MapDialog
import com.iti.azzurra.features.settings.componnents.GpsSwitch
import com.iti.azzurra.features.settings.componnents.LanguageDialog
import com.iti.azzurra.features.settings.componnents.OpenDialogSettingsCard
import com.iti.azzurra.features.settings.componnents.SetCurrentLocationCard
import com.iti.azzurra.features.settings.componnents.TemperatureCard
import com.iti.azzurra.features.settings.componnents.ThemeDialog
import com.iti.azzurra.features.settings.componnents.WindSpeedCard
import com.iti.azzurra.main_navigation.LocalBottomBarHeight
import com.iti.azzurra.main_navigation.LocalHazeState
import com.iti.azzurra.ui.theme.AzzurraTheme
import dev.chrisbanes.haze.hazeEffect

@Composable
fun SettingsRoot(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {
    val hazeState = LocalHazeState.current

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .hazeEffect(state = hazeState)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                OpenDialogSettingsCard(
                    onAction = { onAction(SettingsAction.LanguageDialogToggle(true)) },
                    hazeState = hazeState,
                    iconId = R.drawable.ic_language,
                    titleId = R.string.language,
                    valueId = state.settings.language.getTitleId(),
                )
            }
            item {
                OpenDialogSettingsCard(
                    onAction = { onAction(SettingsAction.ThemeDialogToggle(true)) },
                    hazeState = hazeState,
                    iconId = R.drawable.ic_theme,
                    titleId = R.string.theme,
                    valueId = state.settings.theme.getTitleId(),
                )
            }
            item {
                GpsSwitch(
                    hazeState = hazeState,
                    selectedLocationSource = state.settings.locationSource,
                    onAction = onAction
                )
            }
            item {
                SetCurrentLocationCard(
                    hazeState = hazeState,
                    openMap = {
                        onAction(SettingsAction.MapDialogToggle(true))
                    },
                    openGps = {
                        onAction(SettingsAction.GetCurrentLocation)
                    }
                )
            }
            item {
                TemperatureCard(
                    hazeState = hazeState,
                    selectedUnit = state.settings.temperatureUnit,
                    onAction = onAction
                )
            }
            item {
                WindSpeedCard(
                    hazeState = hazeState,
                    selectedUnit = state.settings.windSpeedUnit,
                    onAction = onAction
                )
            }
            item {
                Spacer(
                    modifier = Modifier.height(LocalBottomBarHeight.current)
                )
            }
        }
    }

    if (state.showLanguageDialog) {
        LanguageDialog(
            onDismissRequest = { onAction(SettingsAction.LanguageDialogToggle(false)) },
            initial = state.settings.language
        )
    }
    if (state.showThemeDialog) {
        ThemeDialog(
            onDismissRequest = { onAction(SettingsAction.ThemeDialogToggle(false)) },
            setTheme = { onAction(SettingsAction.UpdateTheme(it)) },
            selectedTheme = state.settings.theme
        )
    }
    if (state.shouldShowMapDialog) {
        MapDialog(
            onDismissRequest = { onAction(SettingsAction.MapDialogToggle(false)) }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SettingsScreenPreview() {
    AzzurraTheme {
        SettingsScreen(
            state = SettingsState(),
            onAction = {}
        )
    }
}