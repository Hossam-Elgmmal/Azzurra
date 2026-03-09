package com.iti.azzurra.data.weather.local

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalWeatherDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLocalWeatherDataSource(
        localWeatherDataSourceImp: LocalWeatherDataSourceImp
    ): LocalWeatherDataSource

}