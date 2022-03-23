package com.unava.dia.weatherforecast.ui.fragments.future

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unava.dia.weatherforecast.data.api.ApiInterface
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
class FragmentFutureViewModel @Inject constructor(
    private val api: ApiInterface,
    private val repository: WeatherRepository,
) : ViewModel() {
    val error: MutableLiveData<String> = MutableLiveData()
    var futureWeather: MutableLiveData<FutureWeatherResponse> = MutableLiveData()
    var idMutable: MutableLiveData<Long> = MutableLiveData()

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    var id: Long? = null

    fun setId(currentId: Long) {
        id = currentId
    }

    fun getFutureWeather(country: String, days: Int) {
        scope.launch {
            try {
                val response = api.getFutureWeatherAsync(country, days)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        futureWeather.postValue(data!!)
                        saveToDb(data)
                    } else {
                        //error.postValue("Code404")
                        futureWeather.postValue(id?.let { repository.getFutureWeather(it) })
                    }
                } else {
                    //error.postValue(response.errorBody().toString())
                    futureWeather.postValue(id?.let { repository.getFutureWeather(it) })
                }
            } catch (e: Exception) {
                e.printStackTrace()
                //error.postValue(e.localizedMessage)
                futureWeather.postValue(id?.let { repository.getFutureWeather(it) })
            }
        }
    }

    private fun saveToDb(weather: FutureWeatherResponse) {
        scope.launch {
            idMutable.postValue(repository.insertFutureWeather(weather))
        }
    }
}