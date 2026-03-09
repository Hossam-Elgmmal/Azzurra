package com.iti.azzurra.data.alert.local

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalAlertDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLocalAlertDataSource(
        localAlertDataSourceImp: LocalAlertDataSourceImp
    ): LocalAlertDataSource

}