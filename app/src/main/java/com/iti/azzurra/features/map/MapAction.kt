package com.iti.azzurra.features.map

import androidx.compose.ui.text.input.TextFieldValue
import com.google.android.gms.maps.model.LatLng
import com.iti.azzurra.data.places.models.PlacePrediction

sealed interface MapAction {
    data class OnMapClicked(val latLng: LatLng) : MapAction
    data class OnQueryChanged(val textFieldValue: TextFieldValue) : MapAction
    data class OnPredictionSelected(val place: PlacePrediction): MapAction

    data object OnSetAsCurrentLocation : MapAction
    data object OnAddToFavorites : MapAction

}