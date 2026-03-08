package com.iti.azzurra.data.remote

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteWeatherDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteWeatherDataSource(
        remoteWeatherDataSourceImp: RemoteWeatherDataSourceImp
    ): RemoteWeatherDataSource

}