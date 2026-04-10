package com.iti.azzurra.data.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.iti.azzurra.data.alert.AlertRepo
import com.iti.azzurra.data.weather.WeatherRepo
import com.iti.azzurra.utils.Constants.ERROR_TAG
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class AlertWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val weatherRepo: WeatherRepo,
    private val alertRepo: AlertRepo,
): CoroutineWorker(
    context,
    workerParameters
) {
    override suspend fun doWork(): Result {
        Log.e(ERROR_TAG, "doWork: is running")
        val alerts = alertRepo.getAllAlertsOnce()
        if (alerts.isEmpty()) {
            return Result.success()
        }
        return Result.success()
    }
}