package com.iti.azzurra.features.map

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iti.azzurra.ui.theme.AzzurraTheme

@Composable
fun MapRoot(
    onDismissRequest: () -> Unit,
    viewModel: MapViewModel = viewModel()
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