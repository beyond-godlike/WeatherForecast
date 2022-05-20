package com.unava.dia.weatherforecast.ui.fragments.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.unava.dia.weatherforecast.data.api.ApiInterface
import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse
import com.unava.dia.weatherforecast.data.model.future.FutureWeatherResponse
import com.unava.dia.weatherforecast.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val api: ApiInterface,
    private val repository: WeatherRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), LifecycleObserver {
    var error: MutableLiveData<String> = MutableLiveData()
    var currentWeather: MutableLiveData<CurrentWeatherResponse> = MutableLiveData()
    var idMutable: MutableLiveData<Long> = MutableLiveData()
    var futureWeather: MutableLiveData<FutureWeatherResponse> = MutableLiveData()

    var id: Long? = null

    fun setId(currentId: Long) {
        id = currentId
    }

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    fun getCurrentWeather(country: String) {
        scope.launch {
            try {
                val response = api.getCurrentWeatherAsync(country)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        currentWeather.postValue(data!!)
                        idMutable.postValue(repository.insertCurrentWeather(data!!))
                    } else {
                        error.postValue("Code404")
                        currentWeather.postValue(id?.let { repository.getCurrentWeather(it) })
                    }
                } else {
                    error.postValue(response.errorBody().toString())
                    currentWeather.postValue(id?.let { repository.getCurrentWeather(it) })
                }
            } catch (e: Exception) {
                error.postValue(e.localizedMessage)
                currentWeather.postValue(id?.let { repository.getCurrentWeather(it) })
            }
        }
    }

    fun getFutureWeather(country: String, days: Int) {
        scope.launch {
            try {
                val response = api.getFutureWeatherAsync(country, days)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        futureWeather.postValue(data!!)
                        idMutable.postValue(repository.insertFutureWeather(data!!))
                    } else {
                        error.postValue("Code404")
                        //futureWeather.postValue(id?.let { repository.getFutureWeather(it) })
                    }
                } else {
                    error.postValue(response.errorBody().toString())
                    //futureWeather.postValue(id?.let { repository.getFutureWeather(it) })
                }
            } catch (e: Exception) {
                //error.postValue(e.localizedMessage)
                futureWeather.postValue(id?.let { repository.getFutureWeather(it) })
            }
        }
    }
}