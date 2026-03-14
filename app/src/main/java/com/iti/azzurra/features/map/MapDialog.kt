package com.iti.azzurra.features.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun MapDialog(
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = true,
            decorFitsSystemWindows = false
        )
    ) {
        MapRoot(
            onDismissRequest = onDismissRequest
        )
    }
}