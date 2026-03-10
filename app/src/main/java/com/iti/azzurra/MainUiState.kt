package com.iti.azzurra

import com.iti.azzurra.data.settings.models.UserSettings

sealed interface MainUiState {
    data object Loading : MainUiState
    data class Ready(val userSettings: UserSettings) : MainUiState
}
