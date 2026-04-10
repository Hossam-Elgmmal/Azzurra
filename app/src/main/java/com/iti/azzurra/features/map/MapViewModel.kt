package com.iti.azzurra.features.map

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.iti.azzurra.R
import com.iti.azzurra.common.SnackbarController
import com.iti.azzurra.common.SnackbarEvent
import com.iti.azzurra.core.network.onFailure
import com.iti.azzurra.core.network.onSuccess
import com.iti.azzurra.core.scope.AzzurraDispatchers
import com.iti.azzurra.core.scope.Dispatcher
import com.iti.azzurra.data.places.PlacesRepo
import com.iti.azzurra.data.places.models.PlaceDetails
import com.iti.azzurra.data.places.models.PlacePrediction
import com.iti.azzurra.data.settings.UserSettingsRepo
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.weather.WeatherRepo
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import com.iti.azzurra.data.weather.mappers.makeLocationId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MapViewModel @Inject constructor(
    private val settingsRepo: UserSettingsRepo,
    private val weatherRepo: WeatherRepo,
    private val placesRepo: PlacesRepo,
    @param:Dispatcher(AzzurraDispatchers.DefaultDispatcher) private val defaultDispatcher: CoroutineDispatcher,
    @param:Dispatcher(AzzurraDispatchers.IODispatcher) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _searchTextFieldValue = MutableStateFlow("")
    private val _state = MutableStateFlow(MapState())

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = settingsRepo.settingsFlow.flatMapLatest { settings ->
        _state.map { oldState ->
            oldState.copy(
                settings = settings,
                currentSavedLatLng = LatLng(settings.savedLatitude, settings.savedLongitude),
                selectedPlace = oldState.geoLocation?.toSelectedPlace(settings)
            )
        }
    }
        .flowOn(defaultDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MapState()
        )

    private fun GeoLocationEntity.toSelectedPlace(
        settings: UserSettings
    ): PlaceDetails {
        return PlaceDetails(
            placeId = makeLocationId(latitude, longitude),
            name = localizedNames[settings.language.getCode()]
                ?: nameEn,
            latitude = latitude,
            longitude = longitude,
        )
    }

    init {
        listenForSearchQuery()
    }

    private fun listenForSearchQuery() {
        _searchTextFieldValue
            .onEach {
                if (it.isBlank()) {
                    _state.update { mapState ->
                        mapState.copy(predictions = emptyList())
                    }
                }
            }
            .filter { it.trim().length > 1 }
            .distinctUntilChanged { old, new -> old == new }
            .debounce(300)
            .onEach { query ->
                searchForPredictions(query)
            }
            .flowOn(ioDispatcher)
            .launchIn(viewModelScope)
    }

    suspend fun searchForPredictions(query: String) {
        setIsLoading(true)
        placesRepo.searchPlaces(query)
            .onSuccess { newPredictions ->
                _state.update {
                    it.copy(
                        predictions = newPredictions,
                        isLoading = false
                    )
                }
            }
            .onFailure { setIsLoading(false) }
    }

    fun onAction(action: MapAction) {
        when (action) {
            MapAction.OnAddToFavorites ->
                addSelectedGeoLocationToFavorites()

            is MapAction.OnMapClicked ->
                updateMarkerAndGetGeoLocation(action.latLng)

            is MapAction.OnPredictionSelected ->
                getPlaceDetails(action.place)

            is MapAction.OnQueryChanged ->
                updateSearchBarAndSearchForCity(action.textFieldValue)

            MapAction.OnSetAsCurrentLocation ->
                setSelectedGeoLocationAsCurrentLocation()

        }
    }

    fun setSelectedGeoLocationAsCurrentLocation() {
        viewModelScope.launch {
            _state.value.geoLocation?.let { geoLocation ->
                settingsRepo.updateUserSettings {
                    it.copy(
                        savedLatitude = geoLocation.latitude,
                        savedLongitude = geoLocation.longitude,
                    )
                }
                sendSnackBar(R.string.updated_current_weather)
                //TODO Check alarms
            } ?: run {
                sendSnackBar(R.string.unable_to_set_as_current_location)
            }
        }
    }

    fun updateSearchBarAndSearchForCity(newTextFieldValue: TextFieldValue) {
        _state.update {
            it.copy(
                searchTextFieldValue = newTextFieldValue
            )
        }
        _searchTextFieldValue.update {
            newTextFieldValue.text
        }
    }

    private fun updateMarkerAndGetGeoLocation(latLng: LatLng) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedLatLng = latLng,
                    isLoading = true
                )
            }
            weatherRepo.getReverseGeoCode(
                latLng.latitude,
                latLng.longitude,
            ).onSuccess { geoLocation ->
                _state.update {
                    it.copy(
                        geoLocation = geoLocation,
                        isLoading = false
                    )
                }
            }.onFailure { setIsLoading(false) }
        }
    }

    private fun addSelectedGeoLocationToFavorites() {
        viewModelScope.launch {
            _state.value.geoLocation?.let { geoLocation ->
                weatherRepo.addLocationToFavorites(
                    geoLocation
                )
                sendSnackBar(R.string.city_added_to_favorites)
            } ?: run {
                sendSnackBar(R.string.unable_to_add_city_to_favorites)
            }
        }
    }

    private suspend fun sendSnackBar(messageId: Int) {
        SnackbarController.sendEvent(
            SnackbarEvent(
                messageId = messageId,
            )
        )
    }

    fun getPlaceDetails(place: PlacePrediction) {
        viewModelScope.launch {
            setIsLoading(true)
            placesRepo.getPlaceDetails(place.placeId)
                .onSuccess { placeDetails ->
                    weatherRepo.getReverseGeoCode(
                        placeDetails.latitude,
                        placeDetails.longitude,
                    ).onSuccess { geoLocation ->
                        _searchTextFieldValue.update { "" }
                        setNewPlace(geoLocation, placeDetails)
                    }.onFailure { setIsLoading(false) }
                }.onFailure { setIsLoading(false) }
        }
    }

    private fun setNewPlace(
        geoLocation: GeoLocationEntity,
        placeDetails: PlaceDetails
    ) {
        _state.update { mapState ->
            mapState.copy(
                geoLocation = geoLocation.copy(
                    nameEn = placeDetails.name,
                ),
                predictions = emptyList(),
                searchTextFieldValue = TextFieldValue(
                    text = placeDetails.name,
                    selection = TextRange(placeDetails.name.length)
                ),
                selectedLatLng = LatLng(
                    geoLocation.latitude,
                    geoLocation.longitude,
                ),
                isLoading = false
            )
        }
    }

    private fun setIsLoading(loading: Boolean) {
        _state.update { it.copy(isLoading = loading) }
    }
}