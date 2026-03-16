package com.iti.azzurra.features.favorites

import com.iti.azzurra.R
import com.iti.azzurra.common.SnackbarController
import com.iti.azzurra.common.SnackbarEvent
import com.iti.azzurra.core.network.WeatherDataError
import com.iti.azzurra.core.network.WeatherResult
import com.iti.azzurra.data.settings.UserSettingsRepo
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.weather.WeatherRepo
import com.iti.azzurra.data.weather.local.models.favorites.DailyForecastUi
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val weatherRepo: WeatherRepo = mockk()
    private val settingsRepo: UserSettingsRepo = mockk()

    private val fakeSettings = UserSettings(savedLatitude = 30.0, savedLongitude = 31.0)
    private val fakeSettingsFlow = MutableStateFlow(fakeSettings)
    private val fakeFavoritesFlow = MutableStateFlow<List<FavoriteLocationEntity>>(emptyList())

    private val fakeLocation = FavoriteLocationEntity(
        locationId = "1"
    )

    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { settingsRepo.settingsFlow } returns fakeSettingsFlow
        every { weatherRepo.getFavoriteLocations() } returns fakeFavoritesFlow
        mockkObject(SnackbarController)
        coEvery { SnackbarController.sendEvent(any()) } just Runs

        viewModel = FavoritesViewModel(
            weatherRepo = weatherRepo,
            settingsRepo = settingsRepo,
            defaultDispatcher = testDispatcher,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun toggleSelectedLocation_sets_selectedLocation_and_fetches_weather_when_location_is_not_null() =
        runTest {
            val weatherList = listOf<DailyForecastUi>()
            coEvery {
                weatherRepo.getFavoriteWeather(
                    latitude = any(),
                    longitude = any(),
                    settings = any()
                )
            } returns WeatherResult.Success(weatherList)

            viewModel.toggleSelectedLocation(fakeLocation)
            advanceUntilIdle()
            val state = viewModel.state.first()

            assertEquals(fakeLocation, state.selectedLocation)
            assertEquals(weatherList, state.selectedWeatherList)
            assertFalse(state.isLoading)
        }

    @Test
    fun toggleSelectedLocation_clears_selectedLocation_and_weatherList_when_location_is_null() =
        runTest {
            viewModel.toggleSelectedLocation(null)

            val state = viewModel.state.first()
            assertNull(state.selectedLocation)
            assertTrue(state.selectedWeatherList.isEmpty())
        }

    @Test
    fun deleteLocationFromFavorites_calls_repo_delete_and_sends_snackbar() = runTest {
        coEvery { weatherRepo.deleteFavoriteLocation(fakeLocation) } just Runs

        viewModel.deleteLocationFromFavorites(fakeLocation)

        coVerify { weatherRepo.deleteFavoriteLocation(fakeLocation) }
        coVerify {
            SnackbarController.sendEvent(
                match { it.messageId == R.string.removed_from_favorites }
            )
        }
    }

    @Test
    fun deleteLocationFromFavorites_snackbar_undo_action_readds_location() = runTest {
        coEvery { weatherRepo.deleteFavoriteLocation(fakeLocation) } just Runs
        coEvery { weatherRepo.addLocationToFavorites(fakeLocation) } just Runs

        viewModel.deleteLocationFromFavorites(fakeLocation)

        val snackbarSlot = slot<SnackbarEvent>()
        coVerify { SnackbarController.sendEvent(capture(snackbarSlot)) }

        snackbarSlot.captured.snackbarAction?.action?.invoke()
        coVerify { weatherRepo.addLocationToFavorites(fakeLocation) }
    }

    @Test
    fun getWeatherForLocation_updates_selectedWeatherList_on_success() = runTest {
        val weatherList = listOf<DailyForecastUi>()
        coEvery {
            weatherRepo.getFavoriteWeather(
                latitude = any(),
                longitude = any(),
                settings = any()
            )
        } returns WeatherResult.Success(weatherList)

        viewModel.getWeatherForLocation(fakeLocation.latitude, fakeLocation.longitude)
        advanceUntilIdle()
        val state = viewModel.state.first()
        assertEquals(weatherList, state.selectedWeatherList)
        assertFalse(state.isLoading)
    }

    @Test
    fun getWeatherForLocation_clears_loading_on_failure() = runTest {
        coEvery {
            weatherRepo.getFavoriteWeather(any(), any(), any())
        } returns WeatherResult.Failure(WeatherDataError.UNKNOWN)

        viewModel.getWeatherForLocation(lat = 30.0, lon = 31.0)

        assertFalse(viewModel.state.first().isLoading)
    }
}