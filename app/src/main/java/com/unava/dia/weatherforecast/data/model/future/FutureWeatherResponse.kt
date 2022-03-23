package com.unava.dia.weatherforecast.data.model.future

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unava.dia.weatherforecast.data.model.curernt.Current
import com.unava.dia.weatherforecast.data.model.curernt.Location

@Entity(tableName = "futureWeather")
data class FutureWeatherResponse(
    @PrimaryKey var id: Int? = null,
    var location : Location? = null,
    var current : Current? = null,
    var forecast : Forecast? = null
)
