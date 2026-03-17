package com.iti.azzurra.data.alert.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey(autoGenerate = true)
    val alertId: Int = 0,
    val startTime: Long = 0,
    val endTime: Long = 0,
    val alarmType: AlertType = AlertType.NOTIFICATION,
    val isActive: Boolean = true,
    val minTemperature: Double = 0.0,
    val maxTemperature: Double = 0.0,
    val alertRequirement: AlertRequirement = AlertRequirement.TEMPERATURE,
)
