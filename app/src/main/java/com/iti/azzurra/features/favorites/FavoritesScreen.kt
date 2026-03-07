package com.iti.azzurra.features.favorites

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iti.azzurra.ui.theme.AzzurraTheme

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