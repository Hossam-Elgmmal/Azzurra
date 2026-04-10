package com.iti.azzurra.features.map

import com.google.android.gms.maps.model.LatLng
import com.iti.azzurra.R
import com.iti.azzurra.common.SnackbarController
import com.iti.azzurra.common.SnackbarEvent
import com.iti.azzurra.core.network.WeatherDataError
import com.iti.azzurra.core.network.WeatherResult
import com.iti.azzurra.data.places.PlacesRepo
import com.iti.azzurra.data.places.models.PlacePrediction
import com.iti.azzurra.data.settings.UserSettingsRepo
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.weather.WeatherRepo
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MapViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val settingsRepo: UserSettingsRepo = mockk()
    private val weatherRepo: WeatherRepo = mockk()
    private val placesRepo: PlacesRepo = mockk()

    private val fakeSettings = UserSettings(savedLatitude = 30.0, savedLongitude = 31.0)
    private val fakeSettingsFlow = MutableStateFlow(fakeSettings)

    private lateinit var viewModel: MapViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { settingsRepo.settingsFlow } returns fakeSettingsFlow
        mockkObject(SnackbarController)
        coEvery { SnackbarController.sendEvent(any()) } just Runs

        viewModel = MapViewModel(
            settingsRepo = settingsRepo,
            weatherRepo = weatherRepo,
            placesRepo = placesRepo,
            defaultDispatcher = testDispatcher,
            ioDispatcher = testDispatcher,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun searchForPredictions_updates_predictions_on_success() = runTest {
        val predictions = listOf(
            PlacePrediction(
                placeId = "1",
                primaryText = "",
                secondaryText = ""
            )
        )
        coEvery { placesRepo.searchPlaces("Cairo") } returns Result.success(predictions)

        viewModel.searchForPredictions("Cairo")

        assertEquals(predictions, viewModel.state.first().predictions)
        assertFalse(viewModel.state.first().isLoading)
    }

    @Test
    fun searchForPredictions_clears_loading_on_failure() = runTest {
        coEvery { placesRepo.searchPlaces(any()) } returns Result.failure(Exception())

        viewModel.searchForPredictions("Cairo")

        assertFalse(viewModel.state.first().isLoading)
    }

    @Test
    fun onMapClicked_updates_selectedLatLng_and_geoLocation_on_success() = runTest {
        val latLng = LatLng(30.0, 31.0)
        val geoLocation = GeoLocationEntity(latitude = 30.0, longitude = 31.0, nameEn = "Cairo")
        coEvery { weatherRepo.getReverseGeoCode(30.0, 31.0) } returns WeatherResult.Success(
            geoLocation
        )

        viewModel.onAction(MapAction.OnMapClicked(latLng))

        val state = viewModel.state.first()
        assertEquals(latLng, state.selectedLatLng)
        assertEquals(geoLocation, state.geoLocation)
        assertFalse(state.isLoading)
    }

    @Test
    fun onMapClicked_clears_loading_and_leaves_geoLocation_null_on_failure() = runTest {
        coEvery { weatherRepo.getReverseGeoCode(any(), any()) } returns WeatherResult.Failure(
            WeatherDataError.UNKNOWN
        )

        viewModel.onAction(MapAction.OnMapClicked(LatLng(30.0, 31.0)))

        val state = viewModel.state.first()
        assertFalse(state.isLoading)
        assertNull(state.geoLocation)
    }

    @Test
    fun onSetAsCurrentLocation_updates_settings_when_geoLocation_is_available() = runTest {
        val geoLocation = GeoLocationEntity(latitude = 30.0, longitude = 31.0, nameEn = "Cairo")
        coEvery { weatherRepo.getReverseGeoCode(any(), any()) } returns WeatherResult.Success(
            geoLocation
        )
        coEvery { settingsRepo.updateUserSettings(any()) } just Runs

        viewModel.onAction(MapAction.OnMapClicked(LatLng(30.0, 31.0)))
        viewModel.onAction(MapAction.OnSetAsCurrentLocation)

        coVerify { settingsRepo.updateUserSettings(any()) }
        coVerify { SnackbarController.sendEvent(SnackbarEvent(messageId = R.string.updated_current_weather)) }
    }

    @Test
    fun onSetAsCurrentLocation_sends_failure_snackbar_when_no_geoLocation() = runTest {
        viewModel.onAction(MapAction.OnSetAsCurrentLocation)

        coVerify(exactly = 0) { settingsRepo.updateUserSettings(any()) }
        coVerify { SnackbarController.sendEvent(SnackbarEvent(messageId = R.string.unable_to_set_as_current_location)) }
    }
}