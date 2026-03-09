package com.iti.azzurra.data.alert

import com.iti.azzurra.data.alert.local.LocalAlertDataSource
import com.iti.azzurra.data.weather.local.LocalWeatherDataSource
import com.iti.azzurra.data.weather.remote.RemoteWeatherDataSource
import javax.inject.Inject


class AlertRepoImp @Inject constructor(
    private val localSource: LocalAlertDataSource
): AlertRepo {
}