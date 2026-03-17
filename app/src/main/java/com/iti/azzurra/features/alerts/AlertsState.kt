package com.iti.azzurra.features.alerts

import com.iti.azzurra.data.alert.local.models.AlertEntity
import com.iti.azzurra.data.settings.models.UserSettings

data class AlertsState(
    val showAddAlertDialog: Boolean = false,
    val shouldAskForPermission: Boolean = false,
    val showNotificationPermissionDialog: Boolean = false,
    val allAlerts: List<AlertEntity> = emptyList(),
    val settings: UserSettings = UserSettings(),
)