package com.iti.azzurra.data.alert

import com.iti.azzurra.data.alert.local.LocalAlertDataSource
import com.iti.azzurra.data.alert.local.models.AlertEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AlertRepoImp @Inject constructor(
    private val localSource: LocalAlertDataSource
) : AlertRepo {
    override fun getAllAlerts(): Flow<List<AlertEntity>> {
        return localSource.getAllAlerts()
    }

    override fun getAlertById(alertId: String): Flow<AlertEntity> {
        return localSource.getAlertById(alertId)
    }

    override suspend fun insertAlert(entity: AlertEntity) {
        localSource.insertAlert(entity)
    }

    override suspend fun deleteAlertById(alertId: Int) {
        localSource.deleteAlertById(alertId)
    }
}