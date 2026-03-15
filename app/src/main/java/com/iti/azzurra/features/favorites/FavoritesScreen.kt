package com.iti.azzurra.features.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ContainedLoadingIndicator
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iti.azzurra.R
import com.iti.azzurra.data.settings.models.LanguageSetting
import com.iti.azzurra.features.favorites.components.EmptyCard
import com.iti.azzurra.features.favorites.components.LocationCard
import com.iti.azzurra.features.favorites.components.WeatherListDialog
import com.iti.azzurra.main_navigation.LocalBottomBarHeight
import com.iti.azzurra.main_navigation.LocalHazeState
import com.iti.azzurra.ui.theme.AzzurraTheme
import dev.chrisbanes.haze.hazeEffect

@Composable
fun FavoritesRoot(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FavoritesScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FavoritesScreen(
    state: FavoritesState,
    onAction: (FavoritesAction) -> Unit,
) {
    val hazeState = LocalHazeState.current

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.favorites),
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

            if (state.favoriteLocations.isEmpty()) {
                item {
                    EmptyCard(hazeState)
                }
            }

            items(state.favoriteLocations, key = { it.locationId }) { location ->
                LocationCard(
                    openWeather = {
                        onAction(FavoritesAction.ToggleSelectedLocation(location))
                    },
                    deleteLocation = {
                        onAction(FavoritesAction.DeleteFavoriteLocation(location))
                    },
                    hazeState = hazeState,
                    language = state.settings.language,
                    favoriteLocationEntity = location
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(LocalBottomBarHeight.current)
                )
            }
        }
    }
    if (state.selectedWeatherList.isNotEmpty() && state.selectedLocation != null) {
        WeatherListDialog(
            onGoBack = {
                onAction(FavoritesAction.ToggleSelectedLocation(null))
            },
            cityName = if (state.settings.language == LanguageSetting.ENGLISH) state.selectedLocation.cityNameEn
            else state.selectedLocation.cityNameAr,
            selectedWeatherList = state.selectedWeatherList
        )
    }
    if (state.isLoading) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                ContainedLoadingIndicator()
            }
        }
    }
}


@Preview
@Composable
private fun FavoritesScreenPreview() {
    AzzurraTheme {
        FavoritesScreen(
            state = FavoritesState(),
            onAction = {}
        )
    }
}