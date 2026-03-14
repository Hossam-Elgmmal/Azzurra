package com.iti.azzurra.data.places

import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.kotlin.awaitFetchPlace
import com.google.android.libraries.places.api.net.kotlin.awaitFindAutocompletePredictions
import com.iti.azzurra.R
import com.iti.azzurra.common.SnackbarController
import com.iti.azzurra.common.SnackbarEvent
import com.iti.azzurra.data.places.models.PlaceDetails
import com.iti.azzurra.data.places.models.PlacePrediction
import javax.inject.Inject

class PlacesRepoImp @Inject constructor(
    private val placesClient: PlacesClient
) : PlacesRepo {

    override suspend fun searchPlaces(queryText: String): Result<List<PlacePrediction>> {
        return runCatching {
            placesClient.awaitFindAutocompletePredictions {
                query = queryText
                typesFilter = listOf(PlaceTypes.CITIES)
            }
                .autocompletePredictions
                .map {
                    PlacePrediction(
                        placeId = it.placeId,
                        primaryText = it.getPrimaryText(null).toString(),
                        secondaryText = it.getSecondaryText(null).toString()
                    )
                }
        }
            .onFailure { error ->
                showErrorMessage(error)
            }
    }

    override suspend fun getPlaceDetails(placeId: String): Result<PlaceDetails> {
        return runCatching {
            val fields = listOf(
                Place.Field.ID,
                Place.Field.DISPLAY_NAME,
                Place.Field.FORMATTED_ADDRESS,
                Place.Field.LOCATION
            )

            val place = placesClient.awaitFetchPlace(placeId, fields).place

            PlaceDetails(
                placeId = place.id ?: "",
                name = place.displayName ?: "",
                address = place.formattedAddress ?: "",
                latitude = place.location?.latitude ?: 0.0,
                longitude = place.location?.longitude ?: 0.0
            )
        }
            .onFailure { error ->
                showErrorMessage(error)
            }
    }

    private suspend fun showErrorMessage(error: Throwable) {
        val message = when ((error as? ApiException)?.statusCode) {
            CommonStatusCodes.NETWORK_ERROR -> R.string.no_internet_connection
            CommonStatusCodes.TIMEOUT -> R.string.request_timed_out
            else -> R.string.something_went_wrong
        }
        SnackbarController.sendEvent(
            SnackbarEvent(
                messageId = message
            )
        )
    }
}