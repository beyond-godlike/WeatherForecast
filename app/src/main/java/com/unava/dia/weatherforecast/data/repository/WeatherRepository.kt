package com.unava.dia.weatherforecast.data.repository

import com.unava.dia.weatherforecast.data.dao.CurrentWeatherResponseDao
import com.unava.dia.weatherforecast.data.dao.FutureWeatherResponseDao
import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse
import com.unava.dia.weatherforecast.data.model.future.FutureWeatherResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val currentWeatherDao: CurrentWeatherResponseDao,
    private val futureWeatherDao: FutureWeatherResponseDao,
) : IWeatherRepository {

    override suspend fun getCurrentWeather(id: Long): CurrentWeatherResponse {
        return currentWeatherDao.getCurrentWeather(id)
    }

    override suspend fun insertCurrentWeather(currentWeather: CurrentWeatherResponse): Long? {
        return currentWeatherDao.insert(currentWeather)
    }

    override suspend fun getFutureWeather(id: Long): FutureWeatherResponse {
        return futureWeatherDao.getFutureWeather(id)
    }

    override suspend fun insertFutureWeather(futureWeather: FutureWeatherResponse): Long? {
        return futureWeatherDao.insert(futureWeather)
    }
}