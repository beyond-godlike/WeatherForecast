package com.unava.dia.weatherforecast.data.api

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.unava.dia.weatherforecast.data.dao.CurrentWeatherResponseDao
import com.unava.dia.weatherforecast.data.dao.FutureWeatherResponseDao
import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse
import com.unava.dia.weatherforecast.data.model.future.FutureWeatherResponse
import com.unava.dia.weatherforecast.utils.WeatherConverter

@Database(entities = [CurrentWeatherResponse::class, FutureWeatherResponse::class],
    version = 1,
    exportSchema = false)
@TypeConverters(
    WeatherConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherResponseDao
    abstract fun futureWeatherDao(): FutureWeatherResponseDao
}