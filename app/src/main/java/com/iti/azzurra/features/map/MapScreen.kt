package com.iti.azzurra.features.map

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.iti.azzurra.R
import com.iti.azzurra.common.AzzurraSnackbarHost
import com.iti.azzurra.common.ObserveEvent
import com.iti.azzurra.common.SnackbarController
import com.iti.azzurra.data.settings.models.ThemeSetting
import com.iti.azzurra.features.map.components.LocationBottomSheetContent
import com.iti.azzurra.features.map.components.MapTopBar
import com.iti.azzurra.ui.theme.AzzurraTheme
import kotlinx.coroutines.launch

@Composable
fun MapRoot(
    onDismissRequest: () -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current.applicationContext
    val snackbarHostState = remember { SnackbarHostState() }

    DisposableEffect(Unit) {
        SnackbarController.showOnDefaultScaffold = false
        onDispose {
            SnackbarController.showOnDefaultScaffold = true
        }
    }

    ObserveEvent(SnackbarController.events) { snackbarEvent ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val result = snackbarHostState.showSnackbar(
                message = context.getString(snackbarEvent.messageId),
                actionLabel = snackbarEvent.snackbarAction?.nameId?.let { context.getString(it) },
                withDismissAction = snackbarEvent.snackbarAction != null
            )
            if (result == SnackbarResult.ActionPerformed) {
                snackbarEvent.snackbarAction?.action?.invoke()
            }
        }
    }

    MapScreen(
        state = state,
        onAction = viewModel::onAction,
        snackbarHostState = snackbarHostState,
        onDismissRequest = onDismissRequest
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    state: MapState,
    onAction: (MapAction) -> Unit,
    snackbarHostState: SnackbarHostState,
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState()
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    LaunchedEffect(state.selectedLatLng) {
        state.selectedLatLng?.let {
            cameraPositionState.animate(CameraUpdateFactory.newLatLng(it))
        }
    }

    LaunchedEffect(state.selectedPlace) {
        if (state.selectedPlace != null) sheetState.bottomSheetState.expand()
        else sheetState.bottomSheetState.hide()
    }

    val isDark = when (state.settings.theme) {
        ThemeSetting.LIGHT -> false
        ThemeSetting.DARK -> true
        ThemeSetting.FOLLOW_SYSTEM -> isSystemInDarkTheme()
    }


    BottomSheetScaffold(
        scaffoldState = sheetState,
        snackbarHost = { AzzurraSnackbarHost(snackbarHostState) },
        sheetContent = {
            state.selectedPlace?.let {
                LocationBottomSheetContent(
                    place = state.selectedPlace,
                    onSetAsCurrentLocation = {
                        onAction(MapAction.OnSetAsCurrentLocation)
                    },
                    onAddToFavorites = {
                        onAction(MapAction.OnAddToFavorites)
                    }
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            GoogleMap(
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    compassEnabled = false,
                    indoorLevelPickerEnabled = false,
                    myLocationButtonEnabled = false,
                    mapToolbarEnabled = false
                ),
                properties = MapProperties(
                    mapStyleOptions =
                        if (isDark) {
                            MapStyleOptions.loadRawResourceStyle(
                                context,
                                R.raw.map_dark_style
                            )
                        } else {
                            null
                        }
                ),
                onMapClick = {
                    onAction(MapAction.OnMapClicked(it))
                },
                modifier = Modifier.fillMaxSize()
            ) {
                state.selectedLatLng?.let { latLng ->
                    Marker(
                        state = remember(latLng) { MarkerState(latLng) },
                    )
                }
            }

            MapTopBar(
                textFieldValue = state.searchTextFieldValue,
                isLoading = state.isLoading,
                predictions = state.predictions,
                onQueryChanged = {
                    onAction(MapAction.OnQueryChanged(it))
                },
                onPredictionSelected = {
                    onAction(MapAction.OnPredictionSelected(it))
                },
                onBack = onDismissRequest
            )
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.4f))
                    .fillMaxWidth()
                    .windowInsetsTopHeight(WindowInsets.statusBars)
                    .align(Alignment.TopCenter)
            )
        }
    }
}

@Preview
@Composable
private fun MapScreenPreview() {
    AzzurraTheme {
        MapScreen(
            state = MapState(),
            onAction = {},
            onDismissRequest = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}