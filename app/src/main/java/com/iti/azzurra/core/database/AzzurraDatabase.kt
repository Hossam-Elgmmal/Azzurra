package com.iti.azzurra.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iti.azzurra.data.alert.local.daos.AlertDao
import com.iti.azzurra.data.alert.local.models.WeatherAlertEntity
import com.iti.azzurra.data.weather.local.daos.FavoriteDao
import com.iti.azzurra.data.weather.local.daos.LocationDao
import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionEntity
import com.iti.azzurra.data.weather.local.models.current_location.DailyForecastEntity
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastEntity
import com.iti.azzurra.data.weather.local.models.current_location.LocationEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteAirPollutionEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteDailyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteHourlyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity

@Database(
    entities = [
        LocationEntity::class,
        HourlyForecastEntity::class,
        DailyForecastEntity::class,
        AirPollutionEntity::class,
        FavoriteLocationEntity::class,
        FavoriteHourlyForecastEntity::class,
        FavoriteDailyForecastEntity::class,
        FavoriteAirPollutionEntity::class,
        WeatherAlertEntity::class
    ],
    version = 1,
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun alertDao(): AlertDao
}