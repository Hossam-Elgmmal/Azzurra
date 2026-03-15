package com.iti.azzurra.features.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.azzurra.R
import com.iti.azzurra.common.SnackbarAction
import com.iti.azzurra.common.SnackbarController
import com.iti.azzurra.common.SnackbarEvent
import com.iti.azzurra.core.network.onFailure
import com.iti.azzurra.core.network.onSuccess
import com.iti.azzurra.core.scope.AzzurraDispatchers
import com.iti.azzurra.core.scope.Dispatcher
import com.iti.azzurra.data.settings.UserSettingsRepo
import com.iti.azzurra.data.weather.WeatherRepo
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
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
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo,
    private val settingsRepo: UserSettingsRepo,
    @param:Dispatcher(AzzurraDispatchers.DefaultDispatcher) private val defaultDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<FavoritesState> = weatherRepo.getFavoriteLocations()
        .flatMapLatest { locations ->
            settingsRepo.settingsFlow.flatMapLatest { settings ->
                _state.map { oldState ->
                    oldState.copy(
                        favoriteLocations = locations,
                        settings = settings
                    )
                }
            }
        }
        .flowOn(defaultDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = FavoritesState()
        )

    fun onAction(action: FavoritesAction) {
        when (action) {
            is FavoritesAction.ToggleSelectedLocation -> {
                toggleSelectedLocation(action.location)
            }

            is FavoritesAction.DeleteFavoriteLocation -> {
                deleteLocationFromFavorites(action.location)
            }
        }
    }

    private fun toggleSelectedLocation(
        location: FavoriteLocationEntity?
    ) {
        if (location == null) {
            _state.update {
                it.copy(
                    selectedWeatherList = emptyList(),
                    selectedLocation = location
                )
            }
        } else {
            _state.update { it.copy(selectedLocation = location) }
            getWeatherForLocation(
                lat = location.latitude,
                lon = location.longitude
            )
        }
    }

    private fun deleteLocationFromFavorites(location: FavoriteLocationEntity) {
        viewModelScope.launch {
            weatherRepo.deleteFavoriteLocation(location)
            SnackbarController.sendEvent(
                SnackbarEvent(
                    messageId = R.string.removed_from_favorites,
                    snackbarAction = SnackbarAction(
                        nameId = R.string.undo,
                        action = {
                            weatherRepo.addLocationToFavorites(location)
                        }
                    )
                )
            )
        }
    }

    private fun getWeatherForLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            setIsLoading(true)
            val settings = state.value.settings
            weatherRepo.getFavoriteWeather(
                latitude = lat,
                longitude = lon,
                settings = settings
            ).onSuccess { newList ->
                _state.update {
                    it.copy(
                        selectedWeatherList = newList,
                        isLoading = false
                    )
                }
            }.onFailure { setIsLoading(false) }
        }
    }

    fun setIsLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

}