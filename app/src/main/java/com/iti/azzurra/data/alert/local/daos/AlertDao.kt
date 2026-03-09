package com.iti.azzurra.data.alert.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iti.azzurra.data.alert.local.models.WeatherAlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {

    @Query("SELECT * FROM alerts ORDER BY startTime ASC")
    fun getAllAlerts(): Flow<List<WeatherAlertEntity>>

    @Query("SELECT * FROM alerts WHERE alertId = :alertId")
    fun getAlertById(alertId: String): Flow<WeatherAlertEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAlert(entity: WeatherAlertEntity)

    @Query("DELETE FROM alerts WHERE alertId = :alertId")
    suspend fun deleteAlert(alertId: String)
}