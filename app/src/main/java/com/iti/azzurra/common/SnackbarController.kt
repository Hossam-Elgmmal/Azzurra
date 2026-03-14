package com.iti.azzurra.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SnackbarController {

    var showOnDefaultScaffold by mutableStateOf(true)
    private val _events = MutableSharedFlow<SnackbarEvent>()
    val events = _events.asSharedFlow()

    suspend fun sendEvent(event: SnackbarEvent) {
        _events.emit(event)
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
