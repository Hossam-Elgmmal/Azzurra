package com.iti.azzurra.data.alert

import com.iti.azzurra.data.alert.local.models.AlertEntity
import kotlinx.coroutines.flow.Flow

interface AlertRepo {
    fun getAllAlerts(): Flow<List<AlertEntity>>
    suspend fun getAllAlertsOnce(): List<AlertEntity>
    fun getAlertById(alertId: String): Flow<AlertEntity>
    suspend fun insertAlert(entity: AlertEntity)
    suspend fun deleteAlertById(alertId: Int)
}