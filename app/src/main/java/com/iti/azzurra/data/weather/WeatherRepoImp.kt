package com.iti.azzurra.data.weather

import android.content.Context
import com.iti.azzurra.common.SnackbarController
import com.iti.azzurra.common.SnackbarEvent
import com.iti.azzurra.core.network.WeatherDataError
import com.iti.azzurra.core.network.WeatherResult
import com.iti.azzurra.core.network.map
import com.iti.azzurra.core.network.onFailure
import com.iti.azzurra.core.network.toUserMessageId
import com.iti.azzurra.core.scope.AzzurraDispatchers
import com.iti.azzurra.core.scope.Dispatcher
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.weather.local.LocalWeatherDataSource
import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionUi
import com.iti.azzurra.data.weather.local.models.current_location.CurrentWeatherUi
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastUi
import com.iti.azzurra.data.weather.local.models.favorites.DailyForecastUi
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import com.iti.azzurra.data.weather.mappers.makeLocationId
import com.iti.azzurra.data.weather.mappers.toAirPollutionEntities
import com.iti.azzurra.data.weather.mappers.toAirPollutionUiList
import com.iti.azzurra.data.weather.mappers.toCurrentWeatherEntity
import com.iti.azzurra.data.weather.mappers.toCurrentWeatherUi
import com.iti.azzurra.data.weather.mappers.toDailyForecast
import com.iti.azzurra.data.weather.mappers.toEntity
import com.iti.azzurra.data.weather.mappers.toFavoriteDailyForecastEntities
import com.iti.azzurra.data.weather.mappers.toFavoriteLocation
import com.iti.azzurra.data.weather.mappers.toHourlyForecastEntities
import com.iti.azzurra.data.weather.mappers.toHourlyForecastUiList
import com.iti.azzurra.data.weather.mappers.toStartOfDayTimestamp
import com.iti.azzurra.data.weather.remote.RemoteWeatherDataSource
import com.iti.azzurra.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class WeatherRepoImp @Inject constructor(
    private val remoteSource: RemoteWeatherDataSource,
    private val localSource: LocalWeatherDataSource,
    @param:ApplicationContext private val context: Context,
    @param:Dispatcher(AzzurraDispatchers.DefaultDispatcher) private val defaultDispatcher: CoroutineDispatcher,
) : WeatherRepo {

    override suspend fun currentWeatherOnce(settings: UserSettings): CurrentWeatherUi? {
        return localSource.getCurrentWeatherOnce(
            makeLocationId(
                settings.savedLatitude,
                settings.savedLongitude
            )
        )?.toCurrentWeatherUi(context, settings)
    }

    override suspend fun hourlyWeatherOnce(settings: UserSettings): List<HourlyForecastUi> {
        return localSource.getHourlyWeatherOnce(
            makeLocationId(
                settings.savedLatitude,
                settings.savedLongitude
            )
        ).toHourlyForecastUiList(context, settings)
    }

    override suspend fun airPollutionOnce(settings: UserSettings): List<AirPollutionUi> {
        return localSource.getAirPollutionOnce(
            makeLocationId(
                settings.savedLatitude,
                settings.savedLongitude
            )
        ).toAirPollutionUiList(context)
    }

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        settings: UserSettings,
    ): WeatherResult<CurrentWeatherUi, WeatherDataError> {
        return remoteSource.getCurrentWeather(
            latitude = latitude,
            longitude = longitude,
        ).map { responseDto ->

            val entity = withContext(defaultDispatcher) {
                responseDto.toCurrentWeatherEntity(makeLocationId(latitude, longitude))
            }
            localSource.insertCurrentWeather(entity)

            entity.toCurrentWeatherUi(context, settings)
        }.onFailure {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    messageId = it.toUserMessageId()
                )
            )
        }
    }

    override suspend fun getHourlyWeather(
        latitude: Double,
        longitude: Double,
        settings: UserSettings,
    ): WeatherResult<List<HourlyForecastUi>, WeatherDataError> {
        return remoteSource.getHourlyForecast(
            latitude = latitude,
            longitude = longitude,
        ).map { responseDto ->
            val entities = withContext(defaultDispatcher) {
                responseDto.toHourlyForecastEntities(makeLocationId(latitude, longitude))
            }
            localSource.insertHourlyForecast(entities)
            entities.toHourlyForecastUiList(context, settings)
        }.onFailure {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    messageId = it.toUserMessageId()
                )
            )
        }
    }

    override suspend fun getAirPollutionForecast(
        latitude: Double,
        longitude: Double,
        settings: UserSettings,
    ): WeatherResult<List<AirPollutionUi>, WeatherDataError> {
        return remoteSource.getAirPollutionForecast(
            latitude = latitude,
            longitude = longitude,
        ).map {
            val entities = withContext(defaultDispatcher) {
                it.toAirPollutionEntities(makeLocationId(latitude, longitude))
            }
            localSource.insertAirPollution(entities)
            entities.toAirPollutionUiList(context)
        }.onFailure {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    messageId = it.toUserMessageId()
                )
            )
        }
    }

    override suspend fun getFavoriteWeather(
        latitude: Double,
        longitude: Double,
        settings: UserSettings,
    ): WeatherResult<List<DailyForecastUi>, WeatherDataError> {

        val todayMillis = System.currentTimeMillis().toStartOfDayTimestamp()

        val localFavorites = localSource.getFavoriteByLocationIdAndLanguageCode(
            makeLocationId(latitude, longitude),
            todayMillis
        ).takeIf { it.size >= Constants.DAILY_FORECAST_ITEM_COUNT }

        if (localFavorites != null) {
            return withContext(defaultDispatcher) {
                WeatherResult.Success(localFavorites.map { it.toDailyForecast(context, settings) })
            }
        }

        return remoteSource.getDailyForecast(
            latitude = latitude,
            longitude = longitude
        ).map { responseDto ->
            val allDays = responseDto.toFavoriteDailyForecastEntities(
                makeLocationId(latitude, longitude),
            )

            localSource.insertFavoriteDailyForecast(allDays)
            withContext(defaultDispatcher) {
                allDays.map { it.toDailyForecast(context, settings) }
            }
        }.onFailure {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    messageId = it.toUserMessageId()
                )
            )
        }
    }

    override suspend fun addLocationToFavorites(geoLocationEntity: GeoLocationEntity) {
        val favorite = geoLocationEntity.toFavoriteLocation()
        localSource.insertFavoriteLocation(favorite)
    }

    override suspend fun addLocationToFavorites(favoriteLocationEntity: FavoriteLocationEntity) {
        localSource.insertFavoriteLocation(favoriteLocationEntity)
    }

    override fun getFavoriteLocations(): Flow<List<FavoriteLocationEntity>> {
        return localSource.getFavoriteLocations()
    }

    override fun getCurrentLocalCity(
        latitude: Double,
        longitude: Double,
    ): Flow<GeoLocationEntity?> {
        return localSource.getGeoLocationByIdFlow(makeLocationId(latitude, longitude))
    }

    override suspend fun getGeoLocationOnce(
        latitude: Double,
        longitude: Double
    ): GeoLocationEntity? {
        return localSource.getGeoLocationByIdOnce(makeLocationId(latitude, longitude))
    }

    override suspend fun deleteFavoriteLocation(
        location: FavoriteLocationEntity
    ) {
        localSource.deleteFavoriteLocation(location.locationId)
    }

    override suspend fun getReverseGeoCode(
        latitude: Double,
        longitude: Double
    ): WeatherResult<GeoLocationEntity, WeatherDataError> {
        val geoLocation = localSource.getGeoLocationByIdOnce(makeLocationId(latitude, longitude))

        if (geoLocation != null) {
            return WeatherResult.Success(geoLocation)
        }

        return remoteSource.getReverseGeoCode(latitude, longitude)
            .map {
                val responseDto = it.firstOrNull()
                val entity = responseDto?.toEntity(latitude, longitude) ?: GeoLocationEntity()
                localSource.insertGeoLocation(entity)

                entity
            }.onFailure {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        messageId = it.toUserMessageId()
                    )
                )
            }
    }

}