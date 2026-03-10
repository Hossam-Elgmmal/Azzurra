package com.iti.azzurra.data.weather.local.models.favorites

import androidx.room.Embedded
import androidx.room.Relation

data class FavoriteWithFullWeather(
    @Embedded
    val location: FavoriteLocationEntity,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val hourlyForecast: List<FavoriteHourlyForecastEntity>,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val dailyForecast: List<FavoriteDailyForecastEntity>,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val airPollution: List<FavoriteAirPollutionEntity>
)