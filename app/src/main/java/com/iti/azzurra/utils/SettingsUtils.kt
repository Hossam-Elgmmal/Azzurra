package com.iti.azzurra.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import com.iti.azzurra.MainUiState
import com.iti.azzurra.data.settings.models.LanguageSetting
import com.iti.azzurra.data.settings.models.ThemeSetting
import com.iti.azzurra.ui.theme.ArabicTypography
import com.iti.azzurra.ui.theme.EnglishTypography


@Composable
fun shouldShowDarkTheme(
    uiState: MainUiState
): Boolean = when (uiState) {
    MainUiState.Loading -> isSystemInDarkTheme()
    is MainUiState.Ready ->
        when (uiState.userSettings.theme) {
            ThemeSetting.LIGHT -> false
            ThemeSetting.DARK -> true
            ThemeSetting.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        }
}

@Composable
fun getTypography(
    uiState: MainUiState
): Typography = when (uiState) {
    MainUiState.Loading -> EnglishTypography
    is MainUiState.Ready ->
        when (uiState.userSettings.language) {
            LanguageSetting.ENGLISH -> EnglishTypography
            LanguageSetting.ARABIC -> ArabicTypography
        }
}