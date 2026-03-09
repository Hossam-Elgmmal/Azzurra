package com.iti.azzurra.data.weather.local.models.favorites

import androidx.room.Embedded
import androidx.room.Relation

data class FavoriteWithFullWeather(
    @Embedded
    val location: com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val hourlyForecast: List<com.iti.azzurra.data.weather.local.models.favorites.FavoriteHourlyForecastEntity>,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val dailyForecast: List<com.iti.azzurra.data.weather.local.models.favorites.FavoriteDailyForecastEntity>,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val airPollution: List<com.iti.azzurra.data.weather.local.models.favorites.FavoriteAirPollutionEntity>
)