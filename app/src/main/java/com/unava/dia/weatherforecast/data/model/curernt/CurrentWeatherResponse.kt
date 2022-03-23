package com.unava.dia.weatherforecast.data.model.curernt

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currentWeather")
data class CurrentWeatherResponse(
    @PrimaryKey var id: Int? = null,
    var location : Location? = null,
    var current : Current? = null
)