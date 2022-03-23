package com.unava.dia.weatherforecast.data.repository

import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse
import com.unava.dia.weatherforecast.data.model.future.FutureWeatherResponse

interface IWeatherRepository {
    suspend fun getCurrentWeather(id: Long) : CurrentWeatherResponse
    suspend fun insertCurrentWeather(currentWeather: CurrentWeatherResponse) : Long?
    suspend fun getFutureWeather(id: Long) : FutureWeatherResponse
    suspend fun insertFutureWeather(futureWeather: FutureWeatherResponse) : Long?
}