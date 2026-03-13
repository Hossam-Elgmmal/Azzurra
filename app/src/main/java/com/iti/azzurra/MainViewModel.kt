package com.iti.azzurra

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.azzurra.data.settings.UserSettingsRepo
import com.iti.azzurra.data.settings.models.LanguageSetting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userSettingsRepo: UserSettingsRepo
) : ViewModel() {

    val mainUiState: Flow<MainUiState> = userSettingsRepo.settingsFlow
        .map {
            MainUiState.Ready(it)
        }

    fun saveLanguageSettings(languageSetting: LanguageSetting) {
        viewModelScope.launch {
            userSettingsRepo.updateUserSettings {
                it.copy(language = languageSetting)
            }
        }
    }
}