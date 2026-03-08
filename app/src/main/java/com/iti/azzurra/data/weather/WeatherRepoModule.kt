package com.iti.azzurra.data.weather

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherRepoModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepo(
        weatherRepoImp: WeatherRepoImp
    ): WeatherRepo

}