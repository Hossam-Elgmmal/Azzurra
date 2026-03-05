package com.iti.azzurra.features.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iti.azzurra.ui.theme.AzzurraTheme

@Composable
fun HomeRoot(
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    Text(
        text = "Home Screen"
    )
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