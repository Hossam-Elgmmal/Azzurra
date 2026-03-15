package com.iti.azzurra.features.favorites.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Divider() {
    VerticalDivider(
        modifier = Modifier.height(52.dp),
        color = MaterialTheme.colorScheme.outlineVariant,
    )
}