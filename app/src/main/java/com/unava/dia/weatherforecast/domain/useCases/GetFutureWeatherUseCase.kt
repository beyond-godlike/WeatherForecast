package com.unava.dia.weatherforecast.domain.useCases

import com.unava.dia.weatherforecast.data.api.ApiInterface
import com.unava.dia.weatherforecast.data.model.future.FutureWeatherResponse
import retrofit2.Response

class GetFutureWeatherUseCase(private val api: ApiInterface) {
      suspend fun getFutureWeatherAsync(city: String, days: Int) : Response<FutureWeatherResponse> {
        return api.getFutureWeatherAsync(city, days)
    }
}