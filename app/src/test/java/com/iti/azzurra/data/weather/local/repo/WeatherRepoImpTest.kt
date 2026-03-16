package com.iti.azzurra.data.weather.local.repo

import android.content.Context
import com.iti.azzurra.common.SnackbarController
import com.iti.azzurra.core.network.WeatherDataError
import com.iti.azzurra.core.network.WeatherResult
import com.iti.azzurra.data.settings.models.UserSettings
import com.iti.azzurra.data.weather.WeatherRepoImp
import com.iti.azzurra.data.weather.local.LocalWeatherDataSource
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import com.iti.azzurra.data.weather.remote.RemoteWeatherDataSource
import com.iti.azzurra.data.weather.remote.models.daily.DailyForecastResponseDto
import com.iti.azzurra.data.weather.remote.models.geocoding.ReverseGeocodingResponseDto
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepoImpTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val remoteSource: RemoteWeatherDataSource = mockk()
    private val localSource: LocalWeatherDataSource = mockk()
    private val context: Context = mockk()

    private val fakeSettings = UserSettings(savedLatitude = 30.0, savedLongitude = 31.0)

    private val fakeGeoLocation = GeoLocationEntity(
        locationId = "1",
        latitude = 30.0,
        longitude = 31.0,
        nameEn = "Cairo"
    )

    private val fakeFavoriteLocation = FavoriteLocationEntity(
        locationId = "1",
        latitude = 30.0,
        longitude = 31.0,
        cityNameEn = "Cairo"
    )

    private lateinit var repo: WeatherRepoImp

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkObject(SnackbarController)
        coEvery { SnackbarController.sendEvent(any()) } just Runs

        repo = WeatherRepoImp(
            remoteSource = remoteSource,
            localSource = localSource,
            context = context,
            defaultDispatcher = testDispatcher,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun getReverseGeoCode_returns_local_cache_when_it_exists() = runTest {
        coEvery {
            localSource.getGeoLocationByIdOnce(any())
        } returns fakeGeoLocation

        val result = repo.getReverseGeoCode(30.0, 31.0)

        assertTrue(result is WeatherResult.Success<GeoLocationEntity>)

        coVerify(exactly = 0) { remoteSource.getReverseGeoCode(any(), any()) }
    }

    @Test
    fun getReverseGeoCode_fetches_from_remote_and_caches_when_local_is_null() = runTest {
        val fakeDto = ReverseGeocodingResponseDto()
        coEvery { localSource.getGeoLocationByIdOnce(any()) } returns null
        coEvery { remoteSource.getReverseGeoCode(any(), any()) } returns WeatherResult.Success(
            listOf(fakeDto)
        )
        coEvery { localSource.insertGeoLocation(any()) } just Runs

        val result = repo.getReverseGeoCode(30.0, 31.0)

        assertTrue(result is WeatherResult.Success)
        coVerify { localSource.insertGeoLocation(any()) }
    }

    @Test
    fun getReverseGeoCode_sends_snackbar_and_returns_failure_on_remote_error() = runTest {
        coEvery { localSource.getGeoLocationByIdOnce(any()) } returns null
        coEvery {
            remoteSource.getReverseGeoCode(any(), any())
        } returns WeatherResult.Failure(WeatherDataError.NO_INTERNET)

        val result = repo.getReverseGeoCode(30.0, 31.0)

        assertTrue(result is WeatherResult.Failure<WeatherDataError>)
        coVerify { SnackbarController.sendEvent(any()) }
    }

    @Test
    fun getFavoriteWeather_fetches_from_remote_when_cache_is_insufficient() = runTest {
        coEvery {
            localSource.getFavoriteByLocationIdAndLanguageCode(any(), any())
        } returns emptyList()
        coEvery {
            remoteSource.getDailyForecast(any(), any())
        } returns WeatherResult.Success(DailyForecastResponseDto())
        coEvery { localSource.insertFavoriteDailyForecast(any()) } just Runs

        repo.getFavoriteWeather(30.0, 31.0, fakeSettings)

        coVerify { remoteSource.getDailyForecast(latitude = 30.0, longitude = 31.0) }
    }

    @Test
    fun getFavoriteWeather_sends_snackbar_on_remote_failure() = runTest {
        coEvery {
            localSource.getFavoriteByLocationIdAndLanguageCode(any(), any())
        } returns emptyList()
        coEvery {
            remoteSource.getDailyForecast(any(), any())
        } returns WeatherResult.Failure(WeatherDataError.NO_INTERNET)

        val result = repo.getFavoriteWeather(30.0, 31.0, fakeSettings)

        assertTrue(result is WeatherResult.Failure)
        coVerify { SnackbarController.sendEvent(any()) }
    }

    @Test
    fun addLocationToFavorites_inserts_favorite_location_entity() = runTest {
        coEvery { localSource.insertFavoriteLocation(fakeFavoriteLocation) } just Runs

        repo.addLocationToFavorites(fakeFavoriteLocation)

        coVerify { localSource.insertFavoriteLocation(fakeFavoriteLocation) }
    }

    @Test
    fun deleteFavoriteLocation_deletes_by_locationId() = runTest {
        coEvery { localSource.deleteFavoriteLocation(fakeFavoriteLocation.locationId) } just Runs

        repo.deleteFavoriteLocation(fakeFavoriteLocation)

        coVerify { localSource.deleteFavoriteLocation(fakeFavoriteLocation.locationId) }
    }
}