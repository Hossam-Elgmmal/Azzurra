package com.iti.azzurra.features.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iti.azzurra.ui.theme.AzzurraTheme

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

@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {
    Text(
        text = "Settings Screen"
    )
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    AzzurraTheme {
        SettingsScreen(
            state = SettingsState(),
            onAction = {}
        )
    }
}