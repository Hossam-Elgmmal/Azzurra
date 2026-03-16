package com.iti.azzurra.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.azzurra.core.network.onSuccess
import com.iti.azzurra.data.settings.UserSettingsRepo
import com.iti.azzurra.data.usecases.CurrentLocationUseCase
import com.iti.azzurra.data.weather.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo,
    private val settingsRepo: UserSettingsRepo,
    private val locationUseCase: CurrentLocationUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = settingsRepo.settingsFlow
        .flatMapLatest { settings ->
            _state.map {
                it.copy(
                    settings = settings
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeState()
        )

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.FetchNewData -> {
                fetchNewData()
            }
        }
    }

    private fun fetchNewData() {
        viewModelScope.launch {

            val settings = state.value.settings
            val currentWeatherResult = async {
                weatherRepo.getCurrentWeather(
                    latitude = settings.savedLatitude,
                    longitude = settings.savedLongitude,
                    settings = settings
                )
            }

            val hourlyWeatherResult = async {
                weatherRepo.getHourlyWeather(
                    latitude = settings.savedLatitude,
                    longitude = settings.savedLongitude,
                    settings = settings
                )
            }

            val airPollutionResult = async {
                weatherRepo.getAirPollutionForecast(
                    latitude = settings.savedLatitude,
                    longitude = settings.savedLongitude,
                    settings = settings
                )
            }

            currentWeatherResult.await()
                .onSuccess { newCurrentWeather ->
                    _state.update {
                        it.copy(
                            currentWeather = newCurrentWeather
                        )
                    }
                }

            hourlyWeatherResult.await()
                .onSuccess { newHourlyWeather ->
                    _state.update {
                        it.copy(
                            hourlyForecast = newHourlyWeather
                        )
                    }
                }

            airPollutionResult.await()
                .onSuccess { newAirPollution ->
                    _state.update {
                        it.copy(
                            airPollution = newAirPollution
                        )
                    }
                }
        }
    }
}
