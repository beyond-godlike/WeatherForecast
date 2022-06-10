package com.unava.dia.weatherforecast.utils

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import com.unava.dia.weatherforecast.data.WeatherSharedPreferences
import com.unava.dia.weatherforecast.data.repository.WeatherRepository
import com.unava.dia.weatherforecast.domain.useCases.GetCurrentWeatherUseCase
import com.unava.dia.weatherforecast.domain.useCases.GetFutureWeatherUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class UpdateService : Service() {
    @Inject
    lateinit var currentUseCase: GetCurrentWeatherUseCase

    @Inject
    lateinit var futureUseCase: GetFutureWeatherUseCase

    @Inject
    lateinit var shared: WeatherSharedPreferences

    @Inject
    @Named("default")
    lateinit var dispatcher: CoroutineDispatcher

    @Inject
    lateinit var repository: WeatherRepository

    private var id: Long? = null
    private var city: String = Constants.CITY_DEFAULT

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        Toast.makeText(applicationContext, "Service Created", Toast.LENGTH_SHORT).show()

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(applicationContext, "Service Running ", Toast.LENGTH_SHORT).show()

        initData()
        getCurrentWeather()
        getFutureWeather()

        return super.onStartCommand(intent, flags, startId)
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
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
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