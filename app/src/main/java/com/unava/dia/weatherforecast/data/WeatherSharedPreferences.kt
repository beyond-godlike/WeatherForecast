package com.unava.dia.weatherforecast.data

import android.content.Context
import android.content.SharedPreferences
import com.unava.dia.weatherforecast.utils.Constants
import javax.inject.Inject

class WeatherSharedPreferences @Inject constructor(private val app: Context) {

    private lateinit var shared: SharedPreferences

    fun getId(): Long {
        shared = app.getSharedPreferences(Constants.ID_WEATHER, Context.MODE_PRIVATE)
        return shared.getLong(Constants.ID_WEATHER, 0L)
    }

    fun setId(id: Long) {
        shared = app.getSharedPreferences(Constants.ID_WEATHER, Context.MODE_PRIVATE)
        shared.edit().putLong(Constants.ID_WEATHER, id).apply()
    }

    fun getCity(): String {
        shared = app.getSharedPreferences(Constants.CITY, Context.MODE_PRIVATE)
        return shared.getString(Constants.CITY, "London").toString()
    }

    fun saveCity(country: String) {
        shared = app.getSharedPreferences(Constants.CITY, Context.MODE_PRIVATE)
        shared.edit().putString(Constants.CITY, country).apply()
    }
}