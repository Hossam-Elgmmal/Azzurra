package com.iti.azzurra.features.map

import androidx.compose.ui.text.input.TextFieldValue
import com.google.android.gms.maps.model.LatLng
import com.iti.azzurra.data.places.models.PlaceDetails
import com.iti.azzurra.data.places.models.PlacePrediction
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity

data class MapState(
    val settings: UserSettings = UserSettings(),
    val searchTextFieldValue: TextFieldValue = TextFieldValue(""),
    val predictions: List<PlacePrediction> = emptyList(),
    val currentSavedLatLng: LatLng = LatLng(0.0, 0.0),
    val selectedLatLng: LatLng? = null,
    val selectedPlace: PlaceDetails? = null,
    val geoLocation: GeoLocationEntity? = null,
    val isLoading: Boolean = false
)