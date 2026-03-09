package com.iti.azzurra.data.weather

import com.iti.azzurra.data.weather.local.LocalWeatherDataSource
import com.iti.azzurra.data.weather.remote.RemoteWeatherDataSource
import javax.inject.Inject


class WeatherRepoImp @Inject constructor(
    private val remoteSource: RemoteWeatherDataSource,
    private val localSource: LocalWeatherDataSource
): WeatherRepo {
}