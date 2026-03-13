package com.iti.azzurra.data.weather.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iti.azzurra.data.weather.local.models.geo_location.GeoLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GeoLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeoLocation(geolocation: GeoLocationEntity)

    @Query("SELECT * FROM geolocations where locationId = :locationId")
    suspend fun getGeoLocationByIdOnce(locationId: String): GeoLocationEntity?

    @Query("SELECT * FROM geolocations where locationId = :locationId")
    fun getGeoLocationByIdFlow(locationId: String): Flow<GeoLocationEntity?>

    @Query("DELETE FROM geolocations where locationId = :locationId")
    suspend fun deleteGeoLocationById(locationId: String)

}