package com.iti.azzurra.data.weather.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionEntity
import com.iti.azzurra.data.weather.local.models.current_location.CurrentWeatherEntity
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDao {

    @Query("SELECT * FROM current_weather WHERE locationId = :locationId")
    fun getCurrentWeatherByLocation(locationId: String): Flow<CurrentWeatherEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeather: CurrentWeatherEntity)

    @Query("DELETE FROM current_weather WHERE locationId = :locationId")
    suspend fun deleteCurrentWeatherByLocation(locationId: String)

    @Query("DELETE FROM current_weather WHERE timestamp < :timestamp")
    suspend fun deleteExpiredWeatherCache(timestamp: Long)

    @Query("DELETE FROM current_weather")
    suspend fun deleteAllCurrentWeather()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyForecast(entities: List<HourlyForecastEntity>)

    @Query("SELECT * FROM hourly_forecast WHERE locationId = :locationId")
    fun getHourlyWeatherByLocation(locationId: String): Flow<List<HourlyForecastEntity>?>

    @Query("DELETE FROM hourly_forecast WHERE locationId = :locationId")
    suspend fun deleteHourlyWeatherByLocation(locationId: String)

    @Query("DELETE FROM hourly_forecast WHERE timestamp < :timestamp")
    suspend fun deleteExpiredHourlyCache(timestamp: Long)

    @Query("DELETE FROM hourly_forecast")
    suspend fun deleteAllHourly()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAirPollution(entities: List<AirPollutionEntity>)

    @Query("SELECT * FROM air_pollution WHERE locationId = :locationId")
    fun getAirPollutionByLocation(locationId: String): Flow<List<AirPollutionEntity>?>

    @Query("DELETE FROM air_pollution WHERE locationId = :locationId")
    suspend fun deleteAirPollutionByLocation(locationId: String)

    @Query("DELETE FROM air_pollution WHERE timestamp < :timestamp")
    suspend fun deleteExpiredAirPollutionCache(timestamp: Long)

    @Query("DELETE FROM air_pollution")
    suspend fun deleteAllAirPollution()
}