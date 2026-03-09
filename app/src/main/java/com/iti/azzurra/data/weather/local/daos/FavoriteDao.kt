package com.iti.azzurra.data.weather.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteAirPollutionEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteDailyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteHourlyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteWithFullWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Transaction
    @Query("SELECT * FROM favorite_locations")
    fun getAllFavoritesWithFullWeather(): Flow<List<FavoriteWithFullWeather>>

    @Transaction
    @Query("SELECT * FROM favorite_locations WHERE locationId = :locationId")
    fun getFavoriteWithFullWeatherByLocationId(locationId: String): Flow<FavoriteWithFullWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteLocation(entity: FavoriteLocationEntity)

    @Query("DELETE FROM favorite_locations WHERE locationId = :locationId")
    suspend fun deleteFavoriteLocation(locationId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteHourlyForecast(entities: List<FavoriteHourlyForecastEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteDailyForecast(entities: List<FavoriteDailyForecastEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteAirPollution(entities: List<FavoriteAirPollutionEntity>)
}