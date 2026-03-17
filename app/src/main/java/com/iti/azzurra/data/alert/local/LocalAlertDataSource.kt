package com.iti.azzurra.data.alert.local

import com.iti.azzurra.data.alert.local.models.AlertEntity
import kotlinx.coroutines.flow.Flow

interface LocalAlertDataSource {

    fun getAllAlerts(): Flow<List<AlertEntity>>

    fun getAlertById(alertId: String): Flow<AlertEntity>

    suspend fun insertAlert(entity: AlertEntity)

    suspend fun deleteAlertById(alertId: Int)
}