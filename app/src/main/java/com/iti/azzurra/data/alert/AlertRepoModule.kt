package com.iti.azzurra.data.alert

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AlertRepoModule {

    @Binds
    @Singleton
    abstract fun bindAlertRepo(
        alertRepoImp: AlertRepoImp
    ): AlertRepo

}