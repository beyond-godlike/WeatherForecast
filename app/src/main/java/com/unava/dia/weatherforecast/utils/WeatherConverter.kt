package com.unava.dia.weatherforecast.utils

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.unava.dia.weatherforecast.data.model.curernt.Current
import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse
import com.unava.dia.weatherforecast.data.model.curernt.Location
import com.unava.dia.weatherforecast.data.model.future.Forecast

class WeatherConverter {
    @TypeConverter
    fun fromCurrentWeatherResponse(value: CurrentWeatherResponse): String? =
        Moshi.Builder().build().adapter(CurrentWeatherResponse::class.java).toJson(value)

    @TypeConverter
    fun toCurrentWeatherResponse(value: String): CurrentWeatherResponse? =
        value.let { Moshi.Builder().build().adapter(CurrentWeatherResponse::class.java).fromJson(it) }

    @TypeConverter
    fun fromCurrent(value: Current): String? =
        Moshi.Builder().build().adapter(Current::class.java).toJson(value)

    @TypeConverter
    fun toCurrent(value: String): Current? =
        value.let { Moshi.Builder().build().adapter(Current::class.java).fromJson(it) }

    @TypeConverter
    fun fromLocation(value: Location): String? =
        Moshi.Builder().build().adapter(Location::class.java).toJson(value)

    @TypeConverter
    fun toLocation(value: String): Location? =
        value.let { Moshi.Builder().build().adapter(Location::class.java).fromJson(it) }

    @TypeConverter
    fun fromForecast(value: Forecast): String? =
        Moshi.Builder().build().adapter(Forecast::class.java).toJson(value)

    @TypeConverter
    fun toForecast(value: String): Forecast? =
        value.let { Moshi.Builder().build().adapter(Forecast::class.java).fromJson(it) }
}