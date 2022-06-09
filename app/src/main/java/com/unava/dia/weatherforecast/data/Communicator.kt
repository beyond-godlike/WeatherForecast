package com.unava.dia.weatherforecast.data

import com.unava.dia.weatherforecast.data.api.ApiInterface
import com.unava.dia.weatherforecast.data.repository.WeatherRepository
import com.unava.dia.weatherforecast.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class Communicator @Inject constructor(
    private val api: ApiInterface,
    private val repository: WeatherRepository,
    @Named("default")
    private val dispatcher: CoroutineDispatcher,
    private val shared: WeatherSharedPreferences,
) {
    private var id: Long? = null
    private var city: String = Constants.CITY_DEFAULT

    init {
        id = shared.getId()
        city = shared.getCity()
    }

    fun getCurrentWeather() {
        CoroutineScope(dispatcher).launch {
            try {
                val response = api.getCurrentWeatherAsync(city)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        id = repository.insertCurrentWeather(data)
                        id?.let { shared.setId(it) }
                    }
                }
            } catch (e: Exception) {

            }
        }
    }


    fun getFutureWeather() {
        CoroutineScope(dispatcher).launch {
            try {
                val response = api.getFutureWeatherAsync(city, 7)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        id = repository.insertFutureWeather(data)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }
}
