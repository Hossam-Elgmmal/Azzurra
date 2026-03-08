package com.iti.azzurra.data.weather

import com.iti.azzurra.data.remote.RemoteWeatherDataSourceImp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepoImp @Inject constructor(
    private val remoteWeatherDataSource: RemoteWeatherDataSourceImp,
): WeatherRepo {
}