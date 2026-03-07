package com.iti.azzurra.features.map

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iti.azzurra.ui.theme.AzzurraTheme

@Composable
fun MapRoot(
    onDismissRequest: () -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MapScreen(
        state = state,
        onAction = viewModel::onAction,
        onDismissRequest = onDismissRequest
    )
}

@Composable
fun MapScreen(
    state: MapState,
    onAction: (MapAction) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Text(
        text = "Map Screen"
    )
}

@Preview
@Composable
private fun MapScreenPreview() {
    AzzurraTheme {
        MapScreen(
            state = MapState(),
            onAction = {},
            onDismissRequest = {}
        )
    }
}