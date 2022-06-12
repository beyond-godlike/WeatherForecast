package com.unava.dia.weatherforecast.utils

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.unava.dia.weatherforecast.data.WeatherSharedPreferences
import com.unava.dia.weatherforecast.data.repository.WeatherRepository
import com.unava.dia.weatherforecast.domain.useCases.GetCurrentWeatherUseCase
import com.unava.dia.weatherforecast.domain.useCases.GetFutureWeatherUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Named

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted params: WorkerParameters,
    private val currentUseCase: GetCurrentWeatherUseCase,
    private val futureUseCase: GetFutureWeatherUseCase,
    private val shared: WeatherSharedPreferences,
    @Named("default")
    val dispatcher: CoroutineDispatcher,
    private val repository: WeatherRepository,
) : Worker(context, params) {

    private var id: Long? = null
    private var city: String = Constants.CITY_DEFAULT

    override fun doWork(): Result {
        initData()
        getCurrentWeather()
        getFutureWeather()
        return Result.success()
    }

    private fun initData() {
        id = shared.getId()
        city = shared.getCity()
    }

    private fun getCurrentWeather() {
        CoroutineScope(dispatcher).launch {
            try {
                val response = currentUseCase.getCurrentWeatherAsync(city)
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

    private fun getFutureWeather() {
        CoroutineScope(dispatcher).launch {
            try {
                val response = futureUseCase.getFutureWeatherAsync(shared.getCity(), 7)
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