package com.iti.azzurra.data.weather.local

import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionEntity
import com.iti.azzurra.data.weather.local.models.current_location.DailyForecastEntity
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastEntity
import com.iti.azzurra.data.weather.local.models.current_location.LocationEntity
import com.iti.azzurra.data.weather.local.models.current_location.LocationWithFullWeather
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteDailyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import kotlinx.coroutines.flow.Flow

interface LocalWeatherDataSource {

    fun getFullWeatherById(locationId: String): Flow<LocationWithFullWeather?>

    suspend fun insertLocation(entity: LocationEntity)

    suspend fun deleteLocation(locationId: String)

    suspend fun insertHourlyForecast(entities: List<HourlyForecastEntity>)

    suspend fun insertDailyForecast(entities: List<DailyForecastEntity>)

    suspend fun insertAirPollution(entities: List<AirPollutionEntity>)

    fun getFavoriteLocations(): Flow<List<FavoriteLocationEntity>>

    suspend fun getFavoriteByLocationIdAndLanguageCode(
        locationId: String,
        todayMillis: Long
    ): List<FavoriteDailyForecastEntity>

    suspend fun deleteFavoriteLocation(locationId: String)

    suspend fun insertFavoriteDailyForecast(entities: List<FavoriteDailyForecastEntity>)

    suspend fun insertGeoLocation(entity: GeoLocationEntity)

    suspend fun getGeoLocationByIdOnce(locationId: String): GeoLocationEntity?

    fun getGeoLocationByIdFlow(locationId: String): Flow<GeoLocationEntity?>

    suspend fun deleteGeoLocation(locationId: String)

    suspend fun insertFavoriteLocation(favoriteLocation: FavoriteLocationEntity)
}