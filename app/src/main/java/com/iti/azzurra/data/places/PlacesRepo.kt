package com.iti.azzurra.data.places

import com.iti.azzurra.data.places.models.PlaceDetails
import com.iti.azzurra.data.places.models.PlacePrediction

interface PlacesRepo {
    suspend fun searchPlaces(queryText: String): Result<List<PlacePrediction>>
    suspend fun getPlaceDetails(placeId: String): Result<PlaceDetails>
}