package com.iti.azzurra.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.azzurra.core.scope.AzzurraDispatchers
import com.iti.azzurra.core.scope.Dispatcher
import com.iti.azzurra.data.settings.UserSettingsRepo
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.usecases.CurrentLocationUseCase
import com.iti.azzurra.data.weather.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepo: UserSettingsRepo,
    private val weatherRepo: WeatherRepo,
    private val currentLocationUseCase: CurrentLocationUseCase,
    @param:Dispatcher(AzzurraDispatchers.DefaultDispatcher) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState())

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<SettingsState> = settingsRepo.settingsFlow
        .flatMapLatest { userSettings ->
            weatherRepo.getCurrentLocalCity(
                latitude = userSettings.savedLatitude,
                longitude = userSettings.savedLongitude,
            ).flatMapLatest { geoLocation ->
                _state.map { oldState ->
                    oldState.copy(
                        settings = userSettings,
                        cityName = geoLocation?.localizedNames?.get(userSettings.language.getCode())
                            ?: geoLocation?.nameEn ?: ""
                    )
                }
            }
        }
        .flowOn(dispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SettingsState()
        )

    fun onAction(action: SettingsAction) {

        when (action) {
            is SettingsAction.UpdateLocationSource -> {
                updateSettings {
                    it.copy(locationSource = action.locationSource)
                }
            }

            is SettingsAction.UpdateTemperatureUnit -> {
                updateSettings {
                    it.copy(temperatureUnit = action.temperatureUnit)
                }
            }

            is SettingsAction.UpdateTheme -> {
                updateSettings {
                    it.copy(theme = action.theme)
                }
            }

            is SettingsAction.UpdateWindSpeedUnit -> {
                updateSettings {
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

            SettingsAction.GetLocationPermission -> {
                _state.update {
                    it.copy(shouldShowLocationPermissionDialog = true)
                }
            }

            SettingsAction.GetCurrentLocation -> {
                getCurrentLocation()
            }

            SettingsAction.CancelGettingLocationPermission -> {
                _state.update {
                    it.copy(shouldShowLocationPermissionDialog = false)
                }
            }
        }
    }

    private fun updateSettings(
        transform: (UserSettings) -> UserSettings
    ) {
        viewModelScope.launch {
            settingsRepo.updateUserSettings(transform)
        }
    }

    private fun getCurrentLocation() {
        viewModelScope.launch {
            withTimeoutOrNull(20_000) {
                currentLocationUseCase()
            }
        }
    }
}