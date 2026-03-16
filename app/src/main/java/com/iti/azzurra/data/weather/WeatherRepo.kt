package com.iti.azzurra.data.weather

import com.iti.azzurra.core.network.WeatherDataError
import com.iti.azzurra.core.network.WeatherResult
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionUi
import com.iti.azzurra.data.weather.local.models.current_location.CurrentWeatherUi
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastUi
import com.iti.azzurra.data.weather.local.models.favorites.DailyForecastUi
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {

    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        settings: UserSettings,
    ): WeatherResult<CurrentWeatherUi, WeatherDataError>

    suspend fun getHourlyWeather(
        latitude: Double,
        longitude: Double,
        settings: UserSettings,
    ): WeatherResult<List<HourlyForecastUi>, WeatherDataError>

    suspend fun getAirPollutionForecast(
        latitude: Double,
        longitude: Double,
        settings: UserSettings,
    ): WeatherResult<List<AirPollutionUi>, WeatherDataError>

    suspend fun getFavoriteWeather(
        latitude: Double,
        longitude: Double,
        settings: UserSettings,
    ): WeatherResult<List<DailyForecastUi>, WeatherDataError>

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