package com.iti.azzurra.data.weather.local.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iti.azzurra.core.database.WeatherDatabase
import com.iti.azzurra.data.weather.local.LocalWeatherDataSourceImp
import com.iti.azzurra.data.weather.local.models.current_location.AirPollutionEntity
import com.iti.azzurra.data.weather.local.models.current_location.CurrentWeatherEntity
import com.iti.azzurra.data.weather.local.models.current_location.HourlyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.DailyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.java

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class LocalWeatherDataSourceImpTest {

    private lateinit var database: WeatherDatabase
    private lateinit var dataSource: LocalWeatherDataSourceImp

    private val locationId = "1"
    private val fakeCurrentWeather = CurrentWeatherEntity(locationId = locationId)
    private val fakeHourlyList = listOf(HourlyForecastEntity(locationId = locationId))
    private val fakeAirPollutionList = listOf(AirPollutionEntity(locationId = locationId))
    private val fakeFavoriteLocation = FavoriteLocationEntity(locationId = locationId)
    private val fakeGeoLocation = GeoLocationEntity(locationId = locationId)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        dataSource = LocalWeatherDataSourceImp(
            currentWeatherDao = database.currentWeatherDao(),
            favoriteDao = database.favoriteDao(),
            geoLocationDao = database.geoLocationDao()
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCurrentWeather_and_getCurrentWeatherOnce_returns_inserted_entity() = runTest {
        dataSource.insertCurrentWeather(fakeCurrentWeather)

        val result = dataSource.getCurrentWeatherOnce(locationId)

        assertNotNull(result)
        assertEquals(locationId, result?.locationId)
    }

    @Test
    fun getCurrentWeatherOnce_returns_null_when_nothing_inserted() = runTest {
        val result = dataSource.getCurrentWeatherOnce("id")

        assertNull(result)
    }

    @Test
    fun insertCurrentWeather_replaces_on_conflict() = runTest {
        dataSource.insertCurrentWeather(fakeCurrentWeather)
        val updated = fakeCurrentWeather.copy(temperatureCelsius = 1.0)
        dataSource.insertCurrentWeather(updated)

        val result = dataSource.getCurrentWeatherOnce(locationId)

        assertEquals(1.0, result?.temperatureCelsius)
    }

    @Test
    fun insertHourlyForecast_and_getHourlyWeatherOnce_returns_correct_list() = runTest {
        dataSource.insertHourlyForecast(fakeHourlyList)

        val result = dataSource.getHourlyWeatherOnce(locationId)

        assertEquals(fakeHourlyList.size, result.size)
    }

    @Test
    fun getHourlyWeatherOnce_returns_empty_list_when_nothing_inserted() = runTest {
        val result = dataSource.getHourlyWeatherOnce("id")

        assertTrue(result.isEmpty())
    }

    @Test
    fun insertAirPollution_and_getAirPollutionOnce_returns_correct_list() = runTest {
        dataSource.insertAirPollution(fakeAirPollutionList)

        val result = dataSource.getAirPollutionOnce(locationId)

        assertEquals(fakeAirPollutionList.size, result.size)
    }

    @Test
    fun insertFavoriteLocation_and_getFavoriteLocations_emits_inserted_location() = runTest {
        dataSource.insertFavoriteLocation(fakeFavoriteLocation)

        val result = dataSource.getFavoriteLocations().first()

        assertEquals(1, result.size)
        assertEquals(locationId, result.first().locationId)
    }

    @Test
    fun deleteFavoriteLocation_removes_location_from_flow() = runTest {
        dataSource.insertFavoriteLocation(fakeFavoriteLocation)
        dataSource.deleteFavoriteLocation(locationId)

        val result = dataSource.getFavoriteLocations().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun getFavoriteByLocationId_excludes_past_entries() = runTest {
        val pastEntry = DailyForecastEntity()
        dataSource.insertFavoriteDailyForecast(listOf(pastEntry))

        val result = dataSource.getFavoriteByLocationIdAndLanguageCode(locationId, 1)

        assertTrue(result.isEmpty())
    }

    @Test
    fun insertGeoLocation_and_getGeoLocationByIdOnce_returns_entity() = runTest {
        dataSource.insertGeoLocation(fakeGeoLocation)

        val result = dataSource.getGeoLocationByIdOnce(locationId)

        assertNotNull(result)
        assertEquals(locationId, result?.locationId)
    }

    @Test
    fun getGeoLocationByIdOnce_returns_null_when_not_found() = runTest {
        val result = dataSource.getGeoLocationByIdOnce("id")

        assertNull(result)
    }

    @Test
    fun getGeoLocationByIdFlow_emits_entity_after_insert() = runTest {
        dataSource.insertGeoLocation(fakeGeoLocation)

        val result = dataSource.getGeoLocationByIdFlow(locationId).first()

        assertNotNull(result)
        assertEquals(locationId, result?.locationId)
    }

    @Test
    fun deleteGeoLocation_removes_entity() = runTest {
        dataSource.insertGeoLocation(fakeGeoLocation)
        dataSource.deleteGeoLocation(locationId)

        val result = dataSource.getGeoLocationByIdOnce(locationId)

        assertNull(result)
    }
}