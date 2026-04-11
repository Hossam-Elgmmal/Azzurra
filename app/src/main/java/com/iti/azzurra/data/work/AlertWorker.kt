package com.iti.azzurra.data.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.iti.azzurra.core.network.onFailure
import com.iti.azzurra.core.network.onSuccess
import com.iti.azzurra.data.alert.AlertRepo
import com.iti.azzurra.data.settings.UserSettingsRepo
import com.iti.azzurra.data.weather.WeatherRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class AlertWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val weatherRepo: WeatherRepo,
    private val alertRepo: AlertRepo,
    private val settingsRepo: UserSettingsRepo,
    private val alertEvaluator: AlertEvaluator
) : CoroutineWorker(
    context,
    workerParameters
) {
    override suspend fun doWork(): Result {
        val alerts = alertRepo.getAllAlertsOnce()
        if (alerts.isEmpty()) {
            return Result.success()
        }
        val settings = settingsRepo.settingsFlow.value
        weatherRepo.getCurrentWeatherEntity(
            settings.savedLatitude, settings.savedLongitude, settings
        ).onSuccess { currentWeatherEntity ->
            alertEvaluator.evaluate(currentWeatherEntity, alerts)
        }.onFailure {
            return Result.retry()
        }
        return Result.success()
    }
}