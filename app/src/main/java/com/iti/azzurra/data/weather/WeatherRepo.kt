package com.iti.azzurra.data.weather

import com.iti.azzurra.core.network.WeatherDataError
import com.iti.azzurra.core.network.WeatherResult
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteDailyForecastEntity
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {

    suspend fun getFavoriteWeather(
        latitude: Double,
        longitude: Double,
        languageCode: String,
    ): WeatherResult<List<FavoriteDailyForecastEntity>, WeatherDataError>

    suspend fun addLocationToFavorites(
        geoLocationEntity: GeoLocationEntity
    )

    suspend fun getReverseGeoCode(
        latitude: Double,
        longitude: Double,
    ): WeatherResult<GeoLocationEntity, WeatherDataError>

    fun getCurrentLocalCity(
        latitude: Double,
        longitude: Double,
    ): Flow<GeoLocationEntity?>
}