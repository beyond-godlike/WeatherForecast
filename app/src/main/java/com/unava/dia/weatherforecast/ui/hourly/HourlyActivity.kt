package com.unava.dia.weatherforecast.ui.hourly

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unava.dia.weatherforecast.R
import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse
import com.unava.dia.weatherforecast.data.model.future.FutureWeatherResponse
import com.unava.dia.weatherforecast.ui.fragments.base.BaseActivity
import com.unava.dia.weatherforecast.ui.fragments.base.SharedViewModel
import com.unava.dia.weatherforecast.utils.GlideUtil
import com.unava.dia.weatherforecast.utils.getDateString
import com.unava.dia.weatherforecast.utils.obtainViewModel
import com.unava.dia.weatherforecast.utils.showError
import java.text.SimpleDateFormat
import java.util.*

class HourlyActivity : BaseActivity() {

    private var rvHourly: RecyclerView? = null
    private var tvLocation: TextView? = null
    private var tvDt: TextView? = null
    private var tvTempH: TextView? = null
    private var ivTemp: ImageView? = null
    private var tvCond: TextView? = null
    private var tvTempMinMax: TextView? = null
    private var tvFeelsLike: TextView? = null

    private var adapter: HourlyAdapter? = null

    private var day = 0

    private val viewModel: SharedViewModel by lazy {
        obtainViewModel(this,
            SharedViewModel::class.java,
            defaultViewModelProviderFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_WeatherForecast)
        setContentView(R.layout.activity_hourly)

        this.initUi()
        this.setupRecyclerView()
        this.observeViewModel()
    }

    override fun initUi() {
        tvLocation = findViewById(R.id.tvLocation)
        tvDt = findViewById(R.id.tvDt)
        tvTempH = findViewById(R.id.tvTempH)
        tvCond = findViewById(R.id.tvCond)
        ivTemp = findViewById(R.id.ivTemp)
        tvTempMinMax = findViewById(R.id.tvTempMinMax)
        tvFeelsLike = findViewById(R.id.tvFeelsLike)
        rvHourly = findViewById(R.id.rvHourly)

        day = intent.getIntExtra("day", 0)
    }

    override fun observeViewModel() {
        viewModel.error.observe(this) {
            showError(it, this)
        }
        viewModel.futureWeather.observe(this) {
            if (it != null) {
                updateView(it)
                updateRv(it)
            } else {
                showError("weather is null", this)
            }
        }
    }

    private fun setupRecyclerView() {
        rvHourly?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    @SuppressLint("SetTextI18n")
    private fun updateView(response: FutureWeatherResponse) {
        tvLocation?.text = response.location?.name + " " + getDateString(
            response.forecast?.forecastday?.get(day)?.date_epoch!!,
            SimpleDateFormat("dd MMMM", Locale.ENGLISH)
        )
        response.forecast?.forecastday?.get(day)?.day?.condition?.icon
        tvDt?.text = "wind : " + response.forecast?.forecastday?.get(day)?.day?.maxwind_kph.toString() +
         ", uv: " + "${response.forecast?.forecastday?.get(day)?.day?.uv}"
        ivTemp?.let { GlideUtil.setImage(it, "https:${response.forecast?.forecastday?.get(day)?.day?.condition?.icon}") }
        tvTempH?.text = "${response.forecast?.forecastday?.get(day)?.day?.avgtemp_c}°C"

        tvCond?.text = "sun:  " + response.forecast?.forecastday?.get(day)?.astro?.sunrise +
                "  -  " + response.forecast?.forecastday?.get(day)?.astro?.sunset
        tvTempMinMax?.text = "moon:  " + response.forecast?.forecastday?.get(day)?.astro?.moonrise +
                "  -  " + response.forecast?.forecastday?.get(day)?.astro?.moonset
        tvFeelsLike?.text = "moonlight:  " + response.forecast?.forecastday?.get(day)?.astro?.moon_illumination +
                ", " + response.forecast?.forecastday?.get(day)?.astro?.moon_phase

    }

    private fun updateRv(response: FutureWeatherResponse) {
        val li = response.forecast?.forecastday?.get(day)?.hour?.toMutableList()
        rvHourly?.visibility = View.VISIBLE
        if (adapter == null) {

            adapter = HourlyAdapter(li!!)
            rvHourly?.adapter = adapter
            //rvHourly?.layoutManager?.scrollToPosition(viewModel.pos.toInt())
        } else {
            rvHourly?.adapter = adapter
            adapter?.addWeather(li!!)
        }
    }
}