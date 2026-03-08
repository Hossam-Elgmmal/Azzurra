package com.iti.azzurra.core.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityMonitor {
    val isOnline: Flow<Boolean>
}