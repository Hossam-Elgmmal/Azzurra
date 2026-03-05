package com.iti.azzurra.features.favorites

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iti.azzurra.ui.theme.AzzurraTheme

@Composable
fun FavoritesRoot(
    viewModel: FavoritesViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FavoritesScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun FavoritesScreen(
    state: FavoritesState,
    onAction: (FavoritesAction) -> Unit,
) {
    Text(
        text = "Favorites Screen"
    )
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