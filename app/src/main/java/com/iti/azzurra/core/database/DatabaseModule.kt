package com.iti.azzurra.core.database

import android.content.Context
import androidx.room.Room
import com.iti.azzurra.data.alert.local.daos.AlertDao
import com.iti.azzurra.data.weather.local.daos.FavoriteDao
import com.iti.azzurra.data.weather.local.daos.GeoLocationDao
import com.iti.azzurra.data.weather.local.daos.LocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWeatherDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        "weather_database"
    ).build()

    @Provides
    @Singleton
    fun provideLocationDao(
        weatherDatabase: WeatherDatabase
    ): LocationDao = weatherDatabase.locationDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(
        weatherDatabase: WeatherDatabase
    ): FavoriteDao = weatherDatabase.favoriteDao()

    @Provides
    @Singleton
    fun provideAlertDao(
        weatherDatabase: WeatherDatabase
    ): AlertDao = weatherDatabase.alertDao()

    @Provides
    @Singleton
    fun provideGeoLocationDao(
        weatherDatabase: WeatherDatabase
    ): GeoLocationDao = weatherDatabase.geoLocationDao()
}