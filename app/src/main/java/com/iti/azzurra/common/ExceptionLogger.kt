package com.iti.azzurra.common

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object ExceptionLogger {

    private val _events = MutableSharedFlow<Throwable>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events = _events.asSharedFlow()

    fun sendEvent(event: Throwable) {
        _events.tryEmit(event)
    }
}
