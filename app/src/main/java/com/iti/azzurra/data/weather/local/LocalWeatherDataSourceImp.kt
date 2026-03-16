package com.iti.azzurra.data.weather.local


import com.iti.azzurra.data.weather.local.daos.CurrentWeatherDao
import com.iti.azzurra.data.weather.local.daos.FavoriteDao
import com.iti.azzurra.data.weather.local.daos.GeoLocationDao
import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionEntity
import com.iti.azzurra.data.weather.local.models.current_location.CurrentWeatherEntity
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.DailyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import javax.inject.Inject

class LocalWeatherDataSourceImp @Inject constructor(
    private val currentWeatherDao: CurrentWeatherDao,
    private val favoriteDao: FavoriteDao,
    private val geoLocationDao: GeoLocationDao
) : LocalWeatherDataSource {

    override suspend fun insertCurrentWeather(
        entity: CurrentWeatherEntity
    ) = currentWeatherDao.insertCurrentWeather(entity)

    override suspend fun insertHourlyForecast(
        entities: List<HourlyForecastEntity>
    ) = currentWeatherDao.insertHourlyForecast(entities)

    override suspend fun insertAirPollution(
        entities: List<AirPollutionEntity>
    ) = currentWeatherDao.insertAirPollution(entities)

    override fun getFavoriteLocations() =
        favoriteDao.getAllFavoriteLocations()

    override suspend fun getFavoriteByLocationIdAndLanguageCode(
        locationId: String,
        todayMillis: Long
    ): List<DailyForecastEntity> {
        return favoriteDao.getFavoriteByLocationIdAndLanguageCode(
            locationId, todayMillis
        )
    }

    override suspend fun insertFavoriteLocation(
        favoriteLocation: FavoriteLocationEntity
    ) : Unit = favoriteDao.insertFavoriteLocation(favoriteLocation)

    override suspend fun deleteFavoriteLocation(
        locationId: String
    ) = favoriteDao.deleteFavoriteLocation(locationId)

    override suspend fun insertFavoriteDailyForecast(
        entities: List<DailyForecastEntity>
    ) = favoriteDao.insertFavoriteDailyForecast(entities)

    override suspend fun insertGeoLocation(
        entity: GeoLocationEntity
    ) = geoLocationDao.insertGeoLocation(entity)

    override suspend fun getGeoLocationByIdOnce(
        locationId: String
    ) = geoLocationDao.getGeoLocationByIdOnce(locationId)

    override fun getGeoLocationByIdFlow(
        locationId: String
    ) = geoLocationDao.getGeoLocationByIdFlow(locationId)

    override suspend fun deleteGeoLocation(
        locationId: String
    ) = geoLocationDao.deleteGeoLocationById(locationId)

}