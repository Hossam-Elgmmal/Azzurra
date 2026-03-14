package com.iti.azzurra.data.places.models

data class PlaceDetails(
    val placeId: String = "",
    val name: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)