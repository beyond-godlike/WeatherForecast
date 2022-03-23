package com.unava.dia.weatherforecast.data.api

import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse
import com.unava.dia.weatherforecast.data.model.future.FutureWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    /**
     * запрос для получения текущей погоды
     */

    @GET("current.json")
    suspend fun getCurrentWeatherAsync(
        @Query("q") q:String
    ): Response<CurrentWeatherResponse>

    /**
     * запрос для получения прогноза погоды
     */
    @GET("forecast.json")
    suspend fun getFutureWeatherAsync(
        @Query("q") location: String,
        @Query("days") days: Int,
    ): Response<FutureWeatherResponse>
}