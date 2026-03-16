package com.iti.azzurra.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iti.azzurra.data.alert.local.daos.AlertDao
import com.iti.azzurra.data.alert.local.models.WeatherAlertEntity
import com.iti.azzurra.data.weather.local.daos.CurrentWeatherDao
import com.iti.azzurra.data.weather.local.daos.FavoriteDao
import com.iti.azzurra.data.weather.local.daos.GeoLocationDao
import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionEntity
import com.iti.azzurra.data.weather.local.models.current_location.CurrentWeatherEntity
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.DailyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocalNamesConverter
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity

@Database(
    entities = [
        CurrentWeatherEntity::class,
        HourlyForecastEntity::class,
        DailyForecastEntity::class,
        AirPollutionEntity::class,
        FavoriteLocationEntity::class,
        WeatherAlertEntity::class,
        GeoLocationEntity::class
    ],
    version = 1,
)
@TypeConverters(GeoLocalNamesConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun alertDao(): AlertDao
    abstract fun geoLocationDao(): GeoLocationDao
}