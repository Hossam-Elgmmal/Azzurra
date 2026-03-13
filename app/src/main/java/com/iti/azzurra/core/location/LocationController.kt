package com.iti.azzurra.core.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationController {
    fun observeLocation(): Flow<Location>
}