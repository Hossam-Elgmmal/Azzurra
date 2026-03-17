package com.iti.azzurra.features.alerts

import com.iti.azzurra.data.alert.local.models.AlertEntity

sealed interface AlertsAction {
    data class OpenAddAlertDialog(val open: Boolean) : AlertsAction
    data class ShowNotificationDialog(val open: Boolean) : AlertsAction
    data class AddAlert(val alert: AlertEntity) : AlertsAction
    data class ToggleActivateAlert(val alert: AlertEntity, val newValue: Boolean) : AlertsAction
    data class DeleteAlert(val alert: AlertEntity) : AlertsAction
}