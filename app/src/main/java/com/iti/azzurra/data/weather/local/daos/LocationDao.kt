package com.iti.azzurra.data.weather.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionEntity
import com.iti.azzurra.data.weather.local.models.current_location.DailyForecastEntity
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastEntity
import com.iti.azzurra.data.weather.local.models.current_location.LocationEntity
import com.iti.azzurra.data.weather.local.models.current_location.LocationWithFullWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Transaction
    @Query("SELECT * FROM locations WHERE locationId = :locationId")
    fun getFullWeatherById(locationId: String): Flow<LocationWithFullWeather?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(entity: LocationEntity)

    @Query("DELETE FROM locations WHERE locationId = :locationId")
    suspend fun deleteLocation(locationId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyForecast(entities: List<HourlyForecastEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyForecast(entities: List<DailyForecastEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAirPollution(entities: List<AirPollutionEntity>)
}