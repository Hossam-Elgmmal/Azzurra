package com.iti.azzurra.core.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.iti.azzurra.common.ExceptionLogger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LocationControllerImp @Inject constructor(
    @param:ApplicationContext private val context: Context
) : LocationController {

    private val locationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    override fun observeLocation(): Flow<Location> {
        return callbackFlow {

            val interval = 5_000L
            val callback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    val location = p0.locations.lastOrNull() ?: return
                    channel.trySend(location)
                }
            }

            val request = LocationRequest
                .Builder(interval)
                .setMinUpdateIntervalMillis(interval)
                .setPriority(PRIORITY_HIGH_ACCURACY)
                .setMinUpdateDistanceMeters(10f)
                .build()

            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(request)
                .setAlwaysShow(true)

            val client = LocationServices.getSettingsClient(context)

            locationClient.requestLocationUpdates(
                request,
                callback,
                context.mainLooper
            )

            client.checkLocationSettings(builder.build())
                .addOnFailureListener { exception ->
                    ExceptionLogger.sendEvent(exception)
                }

            awaitClose {
                locationClient.removeLocationUpdates(callback)
            }
        }
    }
}
