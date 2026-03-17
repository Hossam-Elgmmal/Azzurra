package com.iti.azzurra.data.alert.local

import com.iti.azzurra.data.alert.local.daos.AlertDao
import com.iti.azzurra.data.alert.local.models.AlertEntity
import javax.inject.Inject

class LocalAlertDataSourceImp @Inject constructor(
    private val alertDao: AlertDao
) : LocalAlertDataSource {

    override fun getAllAlerts() = alertDao.getAllAlerts()

    override fun getAlertById(
        alertId: String
    ) = alertDao.getAlertById(alertId)

    override suspend fun insertAlert(
        entity: AlertEntity
    ) = alertDao.insertAlert(entity)

    override suspend fun deleteAlertById(
        alertId: Int
    ) = alertDao.deleteAlert(alertId)
}