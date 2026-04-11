package com.iti.azzurra.data.work

import android.content.Context
import com.iti.azzurra.R
import com.iti.azzurra.data.alert.local.models.AlertEntity
import com.iti.azzurra.data.alert.local.models.AlertRequirement
import com.iti.azzurra.data.alert.local.models.AlertType
import com.iti.azzurra.data.weather.local.models.current_location.CurrentWeatherEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Clock
import javax.inject.Inject
import kotlin.collections.filter

class AlertEvaluator @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val notificationController: NotificationController,
) {

    companion object {
        private val RAIN_ALERT_RANGE = 300 until 600
        private val SNOW_ALERT_RANGE = 600 until 700
    }

    private val clock: Clock = Clock.systemUTC()

    fun evaluate(currentWeather: CurrentWeatherEntity, alerts: List<AlertEntity>) {
        val nowMillis = clock.millis()

        alerts.filter { it.shouldFire(nowMillis) }
            .forEach { alert ->
                val triggered = when (alert.alertRequirement) {
                    AlertRequirement.TEMPERATURE -> alert.isTemperatureOutOfRange(currentWeather)
                    AlertRequirement.RAIN -> currentWeather.isRaining()
                    AlertRequirement.SNOW -> currentWeather.isSnowing()
                }

                if (triggered) dispatchAlert(alert)
            }
    }

    private fun dispatchAlert(alert: AlertEntity) {
        when (alert.alarmType) {
            AlertType.NOTIFICATION -> sendNotification(alert)
            AlertType.ALARM -> triggerAlarm(alert)
        }
    }

    private fun sendNotification(alert: AlertEntity) {
        val (title, body) = alert.notificationContent()
        notificationController.sendNotification(title, body)
    }

    private fun triggerAlarm(alert: AlertEntity) {
        // todo add full screen notification
    }

    private fun AlertEntity.notificationContent(): Pair<String, String> =
        when (alertRequirement) {
            AlertRequirement.TEMPERATURE -> Pair(
                context.getString(R.string.temperature_alert_title),
                context.getString(R.string.temperature_alert_description),
            )

            AlertRequirement.RAIN -> Pair(
                context.getString(R.string.rain_alert_title),
                context.getString(R.string.rain_alert_description),
            )

            AlertRequirement.SNOW -> Pair(
                context.getString(R.string.snow_alert_title),
                context.getString(R.string.snow_alert_description),
            )
        }

    fun AlertEntity.shouldFire(nowMillis: Long): Boolean =
        isActive && nowMillis in startTime..endTime

    fun AlertEntity.isTemperatureOutOfRange(weather: CurrentWeatherEntity): Boolean =
        weather.temperatureCelsius !in minTemperature..maxTemperature

    fun CurrentWeatherEntity.isRaining(): Boolean =
        conditionId in RAIN_ALERT_RANGE

    fun CurrentWeatherEntity.isSnowing(): Boolean =
        conditionId in SNOW_ALERT_RANGE
}