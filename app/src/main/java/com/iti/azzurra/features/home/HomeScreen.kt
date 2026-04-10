package com.iti.azzurra.features.home

import android.Manifest
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iti.azzurra.R
import com.iti.azzurra.common.EmptyCard
import com.iti.azzurra.common.PermissionsDialog
import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionUi
import com.iti.azzurra.features.home.components.AirPollutionCard
import com.iti.azzurra.features.home.components.CurrentWeatherCard
import com.iti.azzurra.features.home.components.HourlyForecastCard
import com.iti.azzurra.main_navigation.LocalBottomBarHeight
import com.iti.azzurra.main_navigation.LocalHazeState
import com.iti.azzurra.ui.theme.AzzurraTheme
import com.iti.azzurra.utils.hasLocationPermission
import dev.chrisbanes.haze.hazeEffect

@Composable
fun HomeRoot(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(state.checkForPermission) {
        if (state.checkForPermission) {
            val hasPermission = context.hasLocationPermission()
            viewModel.onAction(HomeAction.ShowLocationPermissionDialog(!hasPermission))
        }
    }

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    val hazeState = LocalHazeState.current

    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = null,
                        modifier = Modifier
                    )
                },
                title = {
                    val titleAlpha by animateFloatAsState(
                        targetValue = if (isScrolled) 1f else 0f,
                        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                        label = "titleAlpha",
                    )
                    Column {
                        Text(
                            text = state.cityName,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.alpha(titleAlpha),
                        )
                        Text(
                            text = state.currentWeather?.timeText ?: "",
                            modifier = Modifier.alpha(titleAlpha),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                ),
                modifier = Modifier
                    .hazeEffect(state = hazeState)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item("currentWeather") {
                state.currentWeather?.let { weather ->
                    CurrentWeatherCard(
                        weather = weather,
                        cityName = state.cityName,
                        hazeState = hazeState,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                    )
                } ?: EmptyCard(hazeState)
            }

            item("hourlyForecast") {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(state.hourlyForecast, key = { i, _ -> i }) { index, hourly ->
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            HourlyForecastCard(
                                hourly = hourly,
                                hazeState = hazeState,
                                pollution = state.airPollution.getOrElse(index) { AirPollutionUi() },
                                modifier = Modifier
                                    .width(200.dp)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(
                    modifier = Modifier.height(LocalBottomBarHeight.current)
                )
            }
            item {
                Spacer(
                    modifier = Modifier.height(56.dp)
                )
            }
        }
    }
    if (state.showPermissionDialog) {
        PermissionsDialog(
            onDismiss = {
                onAction(HomeAction.ShowLocationPermissionDialog(false))
            },
            titleId = R.string.location_permission,
            textId = R.string.please_allow_azzurra_to_access_your_location,
            onGranted = {
                onAction(HomeAction.ShowLocationPermissionDialog(false))
                onAction(HomeAction.GetCurrentLocation)
            },
            neededPermissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            iconId = R.drawable.ic_location,
            allPermissionsNeeded = false,
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AzzurraTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}