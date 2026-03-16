package com.iti.azzurra.data.weather.local.daos

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iti.azzurra.core.database.WeatherDatabase
import com.iti.azzurra.data.weather.local.models.current_location.CurrentWeatherEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.java

@RunWith(AndroidJUnit4::class)
class CurrentWeatherDaoTest {

    private lateinit var database: WeatherDatabase
    private lateinit var dao: CurrentWeatherDao

    private val fakeWeather = CurrentWeatherEntity()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.currentWeatherDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCurrentWeather_replaces_existing_entry_on_conflict() = runTest {
        dao.insertCurrentWeather(fakeWeather)
        val updated = fakeWeather.copy(temperatureCelsius = 1.0)
        dao.insertCurrentWeather(updated)

        val result = dao.getCurrentWeatherByLocation(fakeWeather.locationId)

        assertEquals(updated, result)
    }

    @Test
    fun getCurrentWeatherByLocation_returns_entity() = runTest {
        dao.insertCurrentWeather(fakeWeather)

        val result = dao.getCurrentWeatherByLocation(fakeWeather.locationId)

        assertEquals(fakeWeather, result)
    }

    @Test
    fun getCurrentWeatherByLocation_returns_null_when_not_found() = runTest {
        val result = dao.getCurrentWeatherByLocation("id")

        assertNull(result)
    }

    @Test
    fun deleteCurrentWeatherByLocation_removes_entity() = runTest {
        dao.insertCurrentWeather(fakeWeather)

        dao.deleteCurrentWeatherByLocation(fakeWeather.locationId)

        val result = dao.getCurrentWeatherByLocation(fakeWeather.locationId)
        assertNull(result)
    }

    @Test
    fun deleteCurrentWeatherByLocation_does_not_affect_other_locations() = runTest {
        val other = fakeWeather.copy(locationId = "1")
        dao.insertCurrentWeather(fakeWeather)
        dao.insertCurrentWeather(other)

        dao.deleteCurrentWeatherByLocation(fakeWeather.locationId)

        val result = dao.getCurrentWeatherByLocation(other.locationId)
        assertNotNull(result)
    }
}