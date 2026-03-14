package com.iti.azzurra.data.usecases

import com.iti.azzurra.R
import com.iti.azzurra.common.SnackbarController
import com.iti.azzurra.common.SnackbarEvent
import com.iti.azzurra.core.location.LocationController
import com.iti.azzurra.core.network.WeatherDataError
import com.iti.azzurra.core.network.WeatherResult
import com.iti.azzurra.core.network.onSuccess
import com.iti.azzurra.data.settings.UserSettingsRepo
import com.iti.azzurra.data.weather.WeatherRepo
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CurrentLocationUseCase @Inject constructor(
    private val settingsRepo: UserSettingsRepo,
    private val locationController: LocationController,
    private val weatherRepo: WeatherRepo,
) {

    suspend operator fun invoke(): WeatherResult<GeoLocationEntity, WeatherDataError> {
        val location = locationController.observeLocation().first()
        return weatherRepo.getReverseGeoCode(
            latitude = location.latitude,
            longitude = location.longitude
        ).onSuccess { newGeoLocation ->
            settingsRepo.updateUserSettings { oldSettings ->
                oldSettings.copy(
                    savedLatitude = newGeoLocation.latitude,
                    savedLongitude = newGeoLocation.longitude,
                )
            }
            SnackbarController.sendEvent(
                SnackbarEvent(
                    messageId = R.string.updated_current_location
                )
            )
        }
    }

}