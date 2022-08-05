package com.unava.dia.weatherforecast.ui.fragments.hourly

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unava.dia.weatherforecast.R
import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse
import com.unava.dia.weatherforecast.data.model.future.FutureWeatherResponse
import com.unava.dia.weatherforecast.ui.fragments.base.BaseFragment
import com.unava.dia.weatherforecast.ui.fragments.base.SharedViewModel
import com.unava.dia.weatherforecast.ui.fragments.future.MounthAdapter
import com.unava.dia.weatherforecast.utils.GlideUtil
import com.unava.dia.weatherforecast.utils.MarginItemDecoration
import com.unava.dia.weatherforecast.utils.obtainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HourlyFragment : BaseFragment(R.layout.fragment_hourly) {

    private var rvHourly: RecyclerView? = null
    private var tvLocation: TextView? = null
    private var tvDt: TextView? = null
    private var tvTemp: TextView? = null
    private var ivTemp: ImageView? = null
    private var tvCond: TextView? = null
    private var tvTempMinMax: TextView? = null
    private var tvFeelsLike: TextView? = null

    private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)

    private var adapter: HourlyAdapter? = null

    private val viewModel: SharedViewModel by lazy {
        obtainViewModel(requireActivity(),
            SharedViewModel::class.java,
            defaultViewModelProviderFactory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initUi()
        this.setupRecyclerView()
        this.bindViewModel()
        this.viewModel.setId(shared.getId())
        this.viewModel.getCurrentWeather(shared.getCity())
    }
    override fun initUi() {
        tvLocation = requireActivity().findViewById(R.id.tvLocation)
        tvDt = requireActivity().findViewById(R.id.tvDt)
        tvTemp = requireActivity().findViewById(R.id.tvTemp)
        tvCond = requireActivity().findViewById(R.id.tvCond)
        ivTemp = requireActivity().findViewById(R.id.ivTemp)
        tvTempMinMax = requireActivity().findViewById(R.id.tvTempMinMax)
        tvFeelsLike = requireActivity().findViewById(R.id.tvFeelsLike)
        rvHourly = requireActivity().findViewById(R.id.rvHourly)
    }

    override fun bindViewModel() {
        this.observeViewModel()
    }
    override fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner) {
            showError(it, requireContext())
        }
        viewModel.futureWeather.observe(viewLifecycleOwner) {
            if(it != null) {
                updateRv(it)
            } else {
                showError("weather is null", requireContext())
            }
        }
        viewModel.currentWeather.observe(viewLifecycleOwner) {
            if (it != null) {
                updateView(it)
            } else {
                showError("weather is null", requireContext())
            }
        }
    }

    private fun setupRecyclerView() {
        rvHourly?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvHourly?.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin))
        )
        //rvMounth?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //rvMounth.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), this))
    }

    @SuppressLint("SetTextI18n")
    private fun updateView(response: CurrentWeatherResponse) {
        tvLocation?.text = response.location?.name + " " + getDateString(response.location?.localtime_epoch!!)
        tvDt?.text = "wind : " + response.current?.wind_kph.toString() + ", pressure: " + response.current?.pressure_in.toString()
        ivTemp?.let { GlideUtil.setImage(it, "https:${response.current?.condition?.icon}") }
        tvTemp?.text = "${response.current?.temp_c}°C"

        tvCond?.text = response.current?.condition?.text
        tvTempMinMax?.text = "${response.current?.uv}"
        tvFeelsLike?.text = "feels like " + "${response.current?.feelslike_c}°C"
    }

    private fun updateRv(response: FutureWeatherResponse) {
        val list = response.forecast?.forecastday?.get(0)?.hour?.toMutableList()
        rvHourly?.visibility = View.VISIBLE
        if (adapter == null) {
            adapter = HourlyAdapter(list!!)
            rvHourly?.adapter = adapter
        } else {
            adapter?.addWeather(list!!)
        }
    }

    private fun getDateString(time: Long) : String = simpleDateFormat.format(time * 1000L)
}