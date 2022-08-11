package com.unava.dia.weatherforecast.ui.fragments.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unava.dia.weatherforecast.data.WeatherSharedPreferences
import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse
import com.unava.dia.weatherforecast.data.model.future.FutureWeatherResponse
import com.unava.dia.weatherforecast.data.repository.WeatherRepository
import com.unava.dia.weatherforecast.domain.useCases.GetCurrentWeatherUseCase
import com.unava.dia.weatherforecast.domain.useCases.GetFutureWeatherUseCase
import com.unava.dia.weatherforecast.utils.getDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: WeatherRepository,
    @Named("default")
    private val dispatcher: CoroutineDispatcher,
    private val currentWeatherUseCase: GetCurrentWeatherUseCase,
    private val futureWeatherUseCase: GetFutureWeatherUseCase,
    private val shared: WeatherSharedPreferences
) : ViewModel(), LifecycleObserver {

    var error: MutableLiveData<String> = MutableLiveData()
    var currentWeather: MutableLiveData<CurrentWeatherResponse?> = MutableLiveData()
    var futureWeather: MutableLiveData<FutureWeatherResponse?> = MutableLiveData()

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + dispatcher
    private val scope = CoroutineScope(coroutineContext)

    lateinit var pos: String

    var id: Long? = null

    fun saveCity(ct: String) {
        shared.saveCity(ct)
    }

    init {
        id = shared.getId()
        getCurrentWeather(shared.getCity())
        getFutureWeather(shared.getCity(), 7)
    }

    fun getCurrentWeather(country: String) {
        scope.launch {
            try {
                val response = currentWeatherUseCase.getCurrentWeatherAsync(country)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        currentWeather.postValue(data)
                        setPos(data.location?.localtime_epoch!!)
                    } else {
                        error.postValue("Code404")
                    }
                } else {
                    error.postValue(response.errorBody().toString())
                }
            } catch (e: Exception) {
                error.postValue(e.localizedMessage)
                currentWeather.postValue(id?.let { repository.getCurrentWeather(it) })
            }
            parentJob.join()
        }
    }

    fun getFutureWeather(country: String, days: Int) {
        scope.launch {
            try {
                val response = futureWeatherUseCase.getFutureWeatherAsync(country, days)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        futureWeather.postValue(data)
                    } else {
                        error.postValue("Code404")
                    }
                } else {
                    error.postValue(response.errorBody().toString())
                }
            } catch (e: Exception) {
                futureWeather.postValue(id?.let { repository.getFutureWeather(it) })
            }
        }
    }

    private fun setPos(time: Long) {
        pos = getDateString(
            time,
            SimpleDateFormat("HH", Locale.ENGLISH)
        )
    }
}