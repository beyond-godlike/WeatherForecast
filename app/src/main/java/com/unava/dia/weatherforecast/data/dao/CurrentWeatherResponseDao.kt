package com.unava.dia.weatherforecast.data.dao

import androidx.room.*
import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse

@Dao
interface CurrentWeatherResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: CurrentWeatherResponse): Long?

    @Update
    suspend fun update(weather: CurrentWeatherResponse)

    @Delete
    suspend fun delete(weather: CurrentWeatherResponse)

    @Query("SELECT * from currentWeather")
    fun getCurrentWeatherList(): List<CurrentWeatherResponse>

    @Query("SELECT * FROM currentWeather WHERE id =:id")
    fun getCurrentWeather(id: Long): CurrentWeatherResponse
}