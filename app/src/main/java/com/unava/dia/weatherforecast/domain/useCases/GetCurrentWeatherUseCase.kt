package com.unava.dia.weatherforecast.domain.useCases

import com.unava.dia.weatherforecast.data.api.ApiInterface
import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse
import retrofit2.Response

class GetCurrentWeatherUseCase(private val api: ApiInterface) {
    suspend fun getCurrentWeatherAsync(city: String) : Response<CurrentWeatherResponse> {
        return api.getCurrentWeatherAsync(city)
    }
}