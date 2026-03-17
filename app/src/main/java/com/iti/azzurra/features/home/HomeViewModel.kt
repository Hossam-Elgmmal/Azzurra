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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo,
    private val settingsRepo: UserSettingsRepo,
    private val locationUseCase: CurrentLocationUseCase,
    @param:Dispatcher(AzzurraDispatchers.DefaultDispatcher) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<HomeState> = settingsRepo.settingsFlow
        .flatMapLatest { userSettings ->
            weatherRepo.getCurrentLocalCity(
                latitude = userSettings.savedLatitude,
                longitude = userSettings.savedLongitude,
            ).flatMapLatest { geoLocation ->
                _state.map { oldState ->
                    oldState.copy(
                        settings = userSettings,
                        cityName = geoLocation?.localizedNames?.get(userSettings.language.getCode())
                            ?: geoLocation?.nameEn ?: userSettings.city,
                        currentLocationId = geoLocation?.locationId ?: "",
                    )
                }
            }
        }
        .flowOn(dispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = HomeState()
        )

    init {
        observeLocationChanges()
    }

    private fun observeLocationChanges() {
        settingsRepo.settingsFlow
            .onEach { userSettings ->
                getNewData(userSettings)
            }
            .distinctUntilChangedBy { settings -> settings.savedLatitude to settings.savedLongitude }
            .onEach { _ ->
                //todo!!
                //fetchNewData()
            }
            .launchIn(viewModelScope)
    }

    private suspend fun getNewData(userSettings: UserSettings) {
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
            HomeAction.FetchNewData -> {
                fetchNewData()
            }

            HomeAction.GetCurrentLocation -> {
                getCurrentLocationWithGps()
                viewModelScope.launch {
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

    private fun getCurrentLocationWithGps() {
        viewModelScope.launch {
            locationUseCase()
        }
    }

    private fun fetchNewData() {
        val settings = state.value.settings
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
