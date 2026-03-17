package com.iti.azzurra.data.alert.local.models

import com.iti.azzurra.R

enum class AlertType {
    ALARM,
    NOTIFICATION;

    fun getLabelId(): Int {
        return when (this) {
            ALARM -> R.string.alarm
            NOTIFICATION -> R.string.notification
        }
    }
}