package com.iti.azzurra.data.weather.local


import com.iti.azzurra.data.weather.local.daos.FavoriteDao
import com.iti.azzurra.data.weather.local.daos.GeoLocationDao
import com.iti.azzurra.data.weather.local.daos.LocationDao
import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionEntity
import com.iti.azzurra.data.weather.local.models.current_location.DailyForecastEntity
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastEntity
import com.iti.azzurra.data.weather.local.models.current_location.LocationEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteDailyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import javax.inject.Inject

class LocalWeatherDataSourceImp @Inject constructor(
    private val locationDao: LocationDao,
    private val favoriteDao: FavoriteDao,
    private val geoLocationDao: GeoLocationDao
) : LocalWeatherDataSource {

    override fun getFullWeatherById(
        locationId: String
    ) = locationDao.getFullWeatherById(locationId)

    override suspend fun insertLocation(
        entity: LocationEntity
    ) = locationDao.insertLocation(entity)

    override suspend fun deleteLocation(
        locationId: String
    ) = locationDao.deleteLocation(locationId)

    override suspend fun insertHourlyForecast(
        entities: List<HourlyForecastEntity>
    ) = locationDao.insertHourlyForecast(entities)

    override suspend fun insertDailyForecast(
        entities: List<DailyForecastEntity>
    ) = locationDao.insertDailyForecast(entities)

    override suspend fun insertAirPollution(
        entities: List<AirPollutionEntity>
    ) = locationDao.insertAirPollution(entities)

    override fun getFavoriteLocations() =
        favoriteDao.getAllFavoriteLocations()

    override suspend fun getFavoriteByLocationIdAndLanguageCode(
        locationId: String,
        todayMillis: Long
    ): List<FavoriteDailyForecastEntity> {
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
        entities: List<FavoriteDailyForecastEntity>
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