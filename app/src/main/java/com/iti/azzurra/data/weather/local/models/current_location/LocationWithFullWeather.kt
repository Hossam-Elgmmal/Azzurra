package com.iti.azzurra.data.weather.local.models.current_location

import androidx.room.Embedded
import androidx.room.Relation

data class LocationWithFullWeather(
    @Embedded
    val location: com.iti.azzurra.data.weather.local.models.current_location.LocationEntity,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val hourlyForecast: List<com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastEntity>,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val dailyForecast: List<com.iti.azzurra.data.weather.local.models.current_location.DailyForecastEntity>,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val airPollution: List<com.iti.azzurra.data.weather.local.models.current_location.AirPollutionEntity>
)