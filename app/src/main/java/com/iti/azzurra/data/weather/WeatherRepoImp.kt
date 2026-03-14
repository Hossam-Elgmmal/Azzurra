package com.iti.azzurra.data.weather

import com.iti.azzurra.common.SnackbarController
import com.iti.azzurra.common.SnackbarEvent
import com.iti.azzurra.core.network.WeatherDataError
import com.iti.azzurra.core.network.WeatherResult
import com.iti.azzurra.core.network.map
import com.iti.azzurra.core.network.onFailure
import com.iti.azzurra.core.network.toUserMessageId
import com.iti.azzurra.data.weather.local.LocalWeatherDataSource
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteDailyForecastEntity
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import com.iti.azzurra.data.weather.mappers.makeLocationId
import com.iti.azzurra.data.weather.mappers.toEntity
import com.iti.azzurra.data.weather.mappers.toFavoriteDailyForecastEntities
import com.iti.azzurra.data.weather.mappers.toFavoriteLocation
import com.iti.azzurra.data.weather.mappers.toStartOfDayTimestamp
import com.iti.azzurra.data.weather.remote.RemoteWeatherDataSource
import com.iti.azzurra.utils.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WeatherRepoImp @Inject constructor(
    private val remoteSource: RemoteWeatherDataSource,
    private val localSource: LocalWeatherDataSource,
) : WeatherRepo {

    override suspend fun getFavoriteWeather(
        latitude: Double,
        longitude: Double,
        languageCode: String,
    ): WeatherResult<List<FavoriteDailyForecastEntity>, WeatherDataError> {

        val todayMillis = System.currentTimeMillis().toStartOfDayTimestamp()

        val localFavorites = localSource.getFavoriteByLocationIdAndLanguageCode(
            makeLocationId(latitude, longitude),
            languageCode,
            todayMillis
        ).takeIf { it.size >= Constants.DAILY_FORECAST_ITEM_COUNT }

        if (localFavorites != null) {
            return WeatherResult.Success(localFavorites)
        }

        return remoteSource.getDailyForecast(
            latitude = latitude,
            longitude = longitude,
            language = languageCode
        ).map {
            val allDays = it.toFavoriteDailyForecastEntities(
                makeLocationId(latitude, longitude),
                languageCode
            )

            localSource.insertFavoriteDailyForecast(allDays)

            allDays
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

    override fun getCurrentLocalCity(
        latitude: Double,
        longitude: Double,
    ): Flow<GeoLocationEntity?> {
        return localSource.getGeoLocationByIdFlow(makeLocationId(latitude, longitude))
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