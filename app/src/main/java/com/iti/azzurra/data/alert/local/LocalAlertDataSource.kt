package com.iti.azzurra.data.alert.local

import com.iti.azzurra.data.alert.local.models.WeatherAlertEntity
import kotlinx.coroutines.flow.Flow

interface LocalAlertDataSource {

    fun getAllAlerts(): Flow<List<WeatherAlertEntity>>

    fun getAlertById(alertId: String): Flow<WeatherAlertEntity>

    suspend fun insertAlert(entity: WeatherAlertEntity)

    suspend fun deleteAlertById(alertId: String)
}