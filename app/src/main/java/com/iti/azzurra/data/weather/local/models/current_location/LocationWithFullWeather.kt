package com.iti.azzurra.data.weather.local.models.current_location

import androidx.room.Embedded
import androidx.room.Relation

data class LocationWithFullWeather(
    @Embedded
    val location: LocationEntity,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val hourlyForecast: List<HourlyForecastEntity>,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val dailyForecast: List<DailyForecastEntity>,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val airPollution: List<AirPollutionEntity>
)