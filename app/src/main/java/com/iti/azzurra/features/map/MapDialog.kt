package com.iti.azzurra.features.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog


@Composable
fun MapDialog(
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        MapRoot(
            onDismissRequest = onDismissRequest
        )
    }
}