package com.iti.azzurra.data.weather

import com.iti.azzurra.core.network.WeatherDataError
import com.iti.azzurra.core.network.WeatherResult
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.weather.local.models.favorites.DailyForecast
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {

    suspend fun getFavoriteWeather(
        latitude: Double,
        longitude: Double,
        settings: UserSettings,
    ): WeatherResult<List<DailyForecast>, WeatherDataError>

    suspend fun addLocationToFavorites(
        geoLocationEntity: GeoLocationEntity
    )

    suspend fun addLocationToFavorites(
        favoriteLocationEntity: FavoriteLocationEntity
    )

    fun getFavoriteLocations(): Flow<List<FavoriteLocationEntity>>

    suspend fun getReverseGeoCode(
        latitude: Double,
        longitude: Double,
    ): WeatherResult<GeoLocationEntity, WeatherDataError>

    fun getCurrentLocalCity(
        latitude: Double,
        longitude: Double,
    ): Flow<GeoLocationEntity?>

    suspend fun getGeoLocationOnce(
        latitude: Double,
        longitude: Double,
    ): GeoLocationEntity?

    suspend fun deleteFavoriteLocation(
        location: FavoriteLocationEntity
    )
}