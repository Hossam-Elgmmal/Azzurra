package com.iti.azzurra.features.alerts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iti.azzurra.ui.theme.AzzurraTheme

@Composable
fun AlertsRoot(
    viewModel: AlertsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AlertsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun AlertsScreen(
    state: AlertsState,
    onAction: (AlertsAction) -> Unit,
) {
    Text(
        text = "Alerts Screen"
    )
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