package com.unava.dia.weatherforecast.ui.fragments.current

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.unava.dia.weatherforecast.R
import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse
import com.unava.dia.weatherforecast.ui.fragments.base.BaseFragment
import com.unava.dia.weatherforecast.utils.GlideUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentCurrent : BaseFragment(R.layout.fragment_current_fragment) {

    private var ct: String = "London"
    private var btOk: Button? = null

    private var tvTemp: TextView? = null
    private var tvCurrentCountry: TextView? = null
    private var tvCondition: TextView? = null
    private var ivCondition: ImageView? = null
    private var etCity: EditText? = null
    private var swTheme: SwitchMaterial? = null

    companion object {
        fun newInstance() = FragmentCurrent()
    }

    private lateinit var viewModel: CurrentViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.initUi()
        this.bindViewModel()
        this.viewModel.setId(getCurrentIdFromShared())
        this.getWeather(ct)
    }


    private fun getWeather(city: String) {
        viewModel.getCurrentWeather(city)
    }

    override fun initUi() {
        btOk = requireActivity().findViewById(R.id.btOk)
        tvTemp = requireActivity().findViewById(R.id.tvTemp)
        tvCurrentCountry = requireActivity().findViewById(R.id.tvCurrentCountry)
        tvCondition = requireActivity().findViewById(R.id.tvCondition)
        ivCondition = requireActivity().findViewById(R.id.imCondition)
        etCity = requireActivity().findViewById(R.id.etCity)
        swTheme = requireActivity().findViewById(R.id.swTheme)

        ct = getCityFromShared()
        if (ct == "") ct = "London"

        btOk?.setOnClickListener {
            getWeather(etCity?.text.toString())
            saveCountryToShared(etCity?.text.toString())
        }
        swTheme?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

    }

    override fun bindViewModel() {
        viewModel = ViewModelProvider(this)[CurrentViewModel::class.java]
        this.observeViewModel()
    }

    override fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner, {
            showError(it, requireContext())
        })
        viewModel.currentWeather.observe(viewLifecycleOwner, {
            updateView(it)
        })
        viewModel.idMutable.observe(viewLifecycleOwner, {
            saveCurrentToShared(it)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateView(response: CurrentWeatherResponse) {
        tvTemp?.text = "${response.current?.temp_c}°C"
        tvCurrentCountry?.text = response.location?.name
        tvCondition?.text = response.current?.condition?.text
        ivCondition?.let { GlideUtil.setImage(it, "https:${response.current?.condition?.icon}") }
    }

}