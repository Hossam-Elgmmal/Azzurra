package com.iti.azzurra.data.weather.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteDailyForecastEntity
import com.iti.azzurra.data.weather.local.models.favorites.FavoriteLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteLocation(favoriteLocation: FavoriteLocationEntity)

    @Query("SELECT * FROM favorite_locations")
    fun getAllFavoriteLocations(): Flow<List<FavoriteLocationEntity>>

    @Query("SELECT * FROM favorite_daily_forecast " +
            "WHERE locationId = :locationId " +
            "AND languageCode = :languageCode " +
            "AND dayTimestamp >= :todayMillis"
    )
    suspend fun getFavoriteByLocationIdAndLanguageCode(
        locationId: String,
        languageCode: String,
        todayMillis: Long
    ): List<FavoriteDailyForecastEntity>

    @Query("DELETE FROM favorite_daily_forecast WHERE locationId = :locationId")
    suspend fun deleteFavoriteLocation(locationId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteDailyForecast(entities: List<FavoriteDailyForecastEntity>)
}