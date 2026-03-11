package com.iti.azzurra.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.azzurra.data.settings.UserSettingsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepo: UserSettingsRepo
) : ViewModel() {

    private val _state: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState())
    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<SettingsState> = settingsRepo.getUserSettingsFlow()
        .flatMapLatest {userSettings ->
            _state.map { oldState ->
                oldState.copy(settings = userSettings)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SettingsState()
        )

    fun onAction(action: SettingsAction) {
        viewModelScope.launch {
            when (action) {
                is SettingsAction.UpdateLocationSource -> {
                    settingsRepo.updateUserSettings {
                        it.copy(locationSource = action.locationSource)
                    }
                }
                is SettingsAction.UpdateTemperatureUnit -> {
                    settingsRepo.updateUserSettings {
                        it.copy(temperatureUnit = action.temperatureUnit)
                    }
                }
                is SettingsAction.UpdateTheme -> {
                    settingsRepo.updateUserSettings {
                        it.copy(theme = action.theme)
                    }
                }
                is SettingsAction.UpdateWindSpeedUnit -> {
                    settingsRepo.updateUserSettings {
                        it.copy(windSpeedUnit = action.windSpeedUnit)
                    }
                }
                is SettingsAction.LanguageDialogToggle -> {
                    _state.update {
                        it.copy(showLanguageDialog = action.open)
                    }
                }
                is SettingsAction.ThemeDialogToggle -> {
                    _state.update {
                        it.copy(showThemeDialog = action.open)
                    }
                }
            }
        }
    }

}