package com.iti.azzurra.core.scope

import com.iti.azzurra.core.scope.AzzurraDispatchers.DefaultDispatcher
import com.iti.azzurra.core.scope.AzzurraDispatchers.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(IODispatcher)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(DefaultDispatcher)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val azzurraDispatcher: AzzurraDispatchers)

enum class AzzurraDispatchers {
    DefaultDispatcher, IODispatcher,
}