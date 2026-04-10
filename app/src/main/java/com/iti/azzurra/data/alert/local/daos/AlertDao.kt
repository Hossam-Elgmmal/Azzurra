package com.iti.azzurra.data.alert.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iti.azzurra.data.alert.local.models.AlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {

    @Query("SELECT * FROM alerts ORDER BY startTime ASC")
    fun getAllAlerts(): Flow<List<AlertEntity>>

    @Query("SELECT * FROM alerts ORDER BY startTime ASC")
    suspend fun getAllAlertsOnce(): List<AlertEntity>

    @Query("SELECT * FROM alerts WHERE alertId = :alertId")
    fun getAlertById(alertId: String): Flow<AlertEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(entity: AlertEntity)

    @Query("DELETE FROM alerts WHERE alertId = :alertId")
    suspend fun deleteAlert(alertId: Int)
}