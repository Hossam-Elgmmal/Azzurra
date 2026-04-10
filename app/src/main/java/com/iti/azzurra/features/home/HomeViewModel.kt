package com.iti.azzurra.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.azzurra.core.network.onSuccess
import com.iti.azzurra.core.scope.AzzurraDispatchers
import com.iti.azzurra.core.scope.Dispatcher
import com.iti.azzurra.data.settings.UserSettingsRepo
import com.iti.azzurra.data.settings.models.LocationSource
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.settings.models.WeatherCondition
import com.iti.azzurra.data.usecases.CurrentLocationUseCase
import com.iti.azzurra.data.weather.WeatherRepo
import com.iti.azzurra.data.work.AlertManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo,
    private val settingsRepo: UserSettingsRepo,
    private val locationUseCase: CurrentLocationUseCase,
    @param:Dispatcher(AzzurraDispatchers.DefaultDispatcher) private val dispatcher: CoroutineDispatcher,
    private val alertManager: AlertManager
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())

    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        observeSettingsChanges()
    }

    private fun observeSettingsChanges() {
        settingsRepo.settingsFlow
            .onEach { settings ->
                getLocalData(settings)
                val geoLocation = weatherRepo.getGeoLocationOnce(
                    latitude = settings.savedLatitude,
                    longitude = settings.savedLongitude,
                )
                _state.update {
                    it.copy(
                        settings = settings,
                        cityName = geoLocation?.localizedNames?.get(settings.language.getCode())
                            ?: geoLocation?.nameEn ?: settings.city,
                        currentLocationId = geoLocation?.locationId ?: "",
                    )
                }
            }
            .distinctUntilChangedBy { settings -> settings.savedLatitude to settings.savedLongitude }
            .onEach { settings ->
                fetchNewData(settings)
                alertManager.runWorkNow()
            }
            .flowOn(dispatcher)
            .launchIn(viewModelScope)
    }

    private suspend fun getLocalData(userSettings: UserSettings) {
        val currentWeather = weatherRepo.currentWeatherOnce(userSettings)
        val hourlyForecast = weatherRepo.hourlyWeatherOnce(userSettings)
        val airPollution = weatherRepo.airPollutionOnce(userSettings)
        _state.update {
            it.copy(
                currentWeather = currentWeather,
                hourlyForecast = hourlyForecast,
                airPollution = airPollution
            )
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.FetchNewData -> {
                fetchNewData(action.settings)
            }

            HomeAction.GetCurrentLocation -> {
                viewModelScope.launch {
                    locationUseCase()
                    settingsRepo.updateUserSettings {
                        it.copy(locationSource = LocationSource.GPS)
                    }
                    _state.update {
                        it.copy(
                            showPermissionDialog = false
                        )
                    }
                }
            }

            is HomeAction.ShowLocationPermissionDialog -> {
                getCurrentLocation(action.needPermission)
            }
        }
    }

    private fun getCurrentLocation(needPermission: Boolean) {
        if (needPermission) {
            _state.update {
                it.copy(
                    showPermissionDialog = true,
                    checkForPermission = false
                )
            }
        } else {
            if (state.value.settings.locationSource == LocationSource.GPS) {
                viewModelScope.launch {
                    locationUseCase()
                }
            }
            _state.update {
                it.copy(
                    showPermissionDialog = false,
                    checkForPermission = false
                )
            }
        }
    }

    private fun fetchNewData(settings: UserSettings) {
        fetchNewCurrentWeather(settings)
        fetchNewHourlyWeather(settings)
        fetchAirPollution(settings)
    }

    private fun fetchAirPollution(settings: UserSettings) {
        viewModelScope.launch {
            weatherRepo.getAirPollutionForecast(
                latitude = settings.savedLatitude,
                longitude = settings.savedLongitude,
                settings = settings
            ).onSuccess { airPollution ->
                _state.update { state ->
                    state.copy(airPollution = airPollution)
                }
            }
        }
    }

    private fun fetchNewHourlyWeather(settings: UserSettings) {
        viewModelScope.launch {
            weatherRepo.getHourlyWeather(
                latitude = settings.savedLatitude,
                longitude = settings.savedLongitude,
                settings = settings
            ).onSuccess { hourlyForecast ->
                _state.update { state ->
                    state.copy(hourlyForecast = hourlyForecast)
                }
            }
        }
    }

    private fun fetchNewCurrentWeather(settings: UserSettings) {
        viewModelScope.launch {
            weatherRepo.getCurrentWeatherUi(
                latitude = settings.savedLatitude,
                longitude = settings.savedLongitude,
                settings = settings
            ).onSuccess { currentWeather ->
                _state.update { state ->
                    state.copy(currentWeather = currentWeather)
                }
                settingsRepo.updateUserSettings {
                    it.copy(
                        weatherCondition = WeatherCondition.getWeatherCondition(currentWeather.conditionId)
                    )
                }
            }
        }
    }
}
