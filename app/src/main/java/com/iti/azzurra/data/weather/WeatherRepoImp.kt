package com.iti.azzurra.data.weather

import com.iti.azzurra.core.network.WeatherDataError
import com.iti.azzurra.core.network.WeatherResult
import com.iti.azzurra.core.network.map
import com.iti.azzurra.data.weather.local.LocalWeatherDataSource
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import com.iti.azzurra.data.weather.mappers.toEntity
import com.iti.azzurra.data.weather.remote.RemoteWeatherDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.math.roundToInt


class WeatherRepoImp @Inject constructor(
    private val remoteSource: RemoteWeatherDataSource,
    private val localSource: LocalWeatherDataSource,
) : WeatherRepo {

    private fun makeLocationId(
        latitude: Double,
        longitude: Double
    ): String {
        return "${(latitude * 100).roundToInt()}_${(longitude * 100).roundToInt()}"
    }

    override fun getCurrentCity(
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
            }
    }

}