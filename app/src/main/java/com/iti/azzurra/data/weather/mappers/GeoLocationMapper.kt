package com.iti.azzurra.data.weather.mappers

import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import com.iti.azzurra.data.weather.remote.models.geocoding.ReverseGeocodingResponseDto
import kotlin.math.roundToInt


fun ReverseGeocodingResponseDto.toEntity(latitude: Double, longitude: Double): GeoLocationEntity {

    return GeoLocationEntity(
        locationId = "${(latitude * 100).roundToInt()}_${(longitude * 100).roundToInt()}",
        nameEn = nameEn ?: "",
        localizedNames = localNames ?: emptyMap(),
        country = country ?: "",
        latitude = latitude,
        longitude = longitude
    )
}

fun GeoLocationEntity.toFavoriteLocation(): FavoriteLocationEntity {
    return FavoriteLocationEntity(
        locationId = locationId,
        cityNameEn = nameEn,
        cityNameAr = localizedNames["ar"] ?: nameEn,
        countryCode = country,
        latitude = latitude,
        longitude = longitude,
    )
}