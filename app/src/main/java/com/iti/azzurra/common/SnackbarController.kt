package com.iti.azzurra.common

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object SnackbarController {

    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent) {
        _events.send(event)
    }
}

data class SnackbarEvent(
    val messageId: Int,
    val snackbarAction: SnackbarAction? = null
)

data class SnackbarAction(
    val nameId: String,
    val action: suspend () -> Unit
)
