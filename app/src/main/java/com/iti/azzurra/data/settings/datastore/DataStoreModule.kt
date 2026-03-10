package com.iti.azzurra.data.settings.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.iti.azzurra.data.settings.models.UserSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context,
        dataStoreSerializer: DataStoreSerializer
    ): DataStore<UserSettings> {
        return DataStoreFactory.create(
            serializer = dataStoreSerializer,
        ) {
            context.dataStoreFile("user_settings.pb")
        }
    }
}