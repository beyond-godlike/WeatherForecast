package com.unava.dia.weatherforecast.ui.fragments.current

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unava.dia.weatherforecast.data.api.ApiInterface
import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse
import com.unava.dia.weatherforecast.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class CurrentViewModel @Inject constructor(
    private val api: ApiInterface,
    private val repository: WeatherRepository,
) : ViewModel() {
    var error: MutableLiveData<String> = MutableLiveData()
    var currentWeather: MutableLiveData<CurrentWeatherResponse> = MutableLiveData()
    var idMutable: MutableLiveData<Long> = MutableLiveData()

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
                        saveToDb(data)
                    } else {
                        //error.postValue("Code404")
                        currentWeather.postValue(id?.let { repository.getCurrentWeather(it) })
                    }
                } else {
                    //error.postValue(response.errorBody().toString())
                    currentWeather.postValue(id?.let { repository.getCurrentWeather(it) })
                }
            } catch (e: Exception) {
                e.printStackTrace()
                //error.postValue(e.localizedMessage)
                currentWeather.postValue(id?.let { repository.getCurrentWeather(it) })
            }
        }
    }

    private fun saveToDb(currentWeather: CurrentWeatherResponse) {
        scope.launch {
            idMutable.postValue(repository.insertCurrentWeather(currentWeather))
        }
    }
}