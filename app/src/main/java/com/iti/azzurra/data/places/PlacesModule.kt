package com.iti.azzurra.data.places

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlacesRepoModule {

    @Binds
    @Singleton
    abstract fun bindPlacesRepo(
        placesRepoImp: PlacesRepoImp
    ): PlacesRepo

}