package com.iti.azzurra.features.favorites.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.iti.azzurra.R
import com.iti.azzurra.data.weather.local.models.favorites.DailyForecast
import com.iti.azzurra.ui.theme.inverseOnSurfaceLight
import com.iti.azzurra.ui.theme.inverseSurfaceLight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherListDialog(
    onGoBack: () -> Unit,
    cityName: String,
    selectedWeatherList: List<DailyForecast>
) {
    Dialog(
        onDismissRequest = onGoBack,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = true,
            decorFitsSystemWindows = false
        )
    ) {
        DailyForecastScreen(
            cityName = cityName,
            selectedWeatherList = selectedWeatherList,
            onGoBack = onGoBack
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyForecastScreen(
    cityName: String,
    selectedWeatherList: List<DailyForecast>,
    onGoBack: () -> Unit,
) {
    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 40 }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = inverseSurfaceLight,
                    scrolledContainerColor = inverseSurfaceLight,
                    navigationIconContentColor = inverseOnSurfaceLight,
                    titleContentColor = inverseOnSurfaceLight,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onGoBack,
                        modifier = Modifier.size(44.dp),
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.go_back),
                        )
                    }
                },
                title = {
                    val titleAlpha by animateFloatAsState(
                        targetValue = if (isScrolled) 1f else 0f,
                        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                        label = "titleAlpha",
                    )
                    Text(
                        text = cityName,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.alpha(titleAlpha),
                    )
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item(key = "header") {
                CityHeader(
                    cityName = cityName,
                    itemCount = selectedWeatherList.size,
                    isScrolled = isScrolled,
                )
            }

            items(
                items = selectedWeatherList,
                key = { forecast -> forecast.dateText },
            ) { forecast ->
                DailyForecastCard(
                    forecast = forecast,
                    modifier = Modifier
                )
            }
        }
    }
}
