package com.iti.azzurra.data.weather.local

import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionEntity
import com.iti.azzurra.data.weather.local.models.current_location.CurrentWeatherEntity
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.DailyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import kotlinx.coroutines.flow.Flow

interface LocalWeatherDataSource {

    suspend fun getCurrentWeatherFlow(locationId: String): CurrentWeatherEntity?
    suspend fun getHourlyWeatherFlow(locationId: String): List<HourlyForecastEntity>
    suspend fun getAirPollutionFlow(locationId: String): List<AirPollutionEntity>

    suspend fun insertCurrentWeather(entity: CurrentWeatherEntity)

    suspend fun insertHourlyForecast(entities: List<HourlyForecastEntity>)

    suspend fun insertAirPollution(entities: List<AirPollutionEntity>)

    fun getFavoriteLocations(): Flow<List<FavoriteLocationEntity>>

    suspend fun getFavoriteByLocationIdAndLanguageCode(
        locationId: String,
        todayMillis: Long
    ): List<DailyForecastEntity>

    suspend fun deleteFavoriteLocation(locationId: String)

    suspend fun insertFavoriteDailyForecast(entities: List<DailyForecastEntity>)

    suspend fun insertGeoLocation(entity: GeoLocationEntity)

    suspend fun getGeoLocationByIdOnce(locationId: String): GeoLocationEntity?

    fun getGeoLocationByIdFlow(locationId: String): Flow<GeoLocationEntity?>

    suspend fun deleteGeoLocation(locationId: String)

    suspend fun insertFavoriteLocation(favoriteLocation: FavoriteLocationEntity)
}