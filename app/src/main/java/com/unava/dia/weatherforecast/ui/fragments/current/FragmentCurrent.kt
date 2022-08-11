package com.unava.dia.weatherforecast.ui.fragments.current

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial
import com.unava.dia.weatherforecast.R
import com.unava.dia.weatherforecast.data.model.curernt.CurrentWeatherResponse
import com.unava.dia.weatherforecast.ui.fragments.base.BaseFragment
import com.unava.dia.weatherforecast.ui.fragments.base.SharedViewModel
import com.unava.dia.weatherforecast.utils.Constants.CITY_DEFAULT
import com.unava.dia.weatherforecast.utils.GlideUtil
import com.unava.dia.weatherforecast.utils.obtainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentCurrent : BaseFragment(R.layout.fragment_current_fragment) {

    private var ct: String = CITY_DEFAULT
    private var btOk: Button? = null

    private var tvTemp: TextView? = null
    private var tvCurrentCountry: TextView? = null
    private var tvCondition: TextView? = null
    private var ivCondition: ImageView? = null
    private var etCity: EditText? = null
    private var swTheme: SwitchMaterial? = null

    private val viewModel: SharedViewModel by lazy {
        obtainViewModel(requireActivity(),
            SharedViewModel::class.java,
            defaultViewModelProviderFactory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initUi()
        this.observeViewModel()
    }

    override fun initUi() {
        btOk = requireActivity().findViewById(R.id.btOk)
        tvTemp = requireActivity().findViewById(R.id.tvTemp)
        tvCurrentCountry = requireActivity().findViewById(R.id.tvCurrentCountry)
        tvCondition = requireActivity().findViewById(R.id.tvCondition)
        ivCondition = requireActivity().findViewById(R.id.imCondition)
        etCity = requireActivity().findViewById(R.id.etCity)
        swTheme = requireActivity().findViewById(R.id.swTheme)

        if (ct == "") ct = CITY_DEFAULT

        btOk?.setOnClickListener {
            ct = etCity?.text.toString()
            viewModel.saveCity(ct)
            viewModel.getCurrentWeather(ct)
            viewModel.getFutureWeather(ct, 7)
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

    override fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner) {
            showError(it, requireContext())
        }
        viewModel.currentWeather.observe(viewLifecycleOwner) {
            if (it != null) {
                updateView(it)
            } else {
                showError("weather is null", requireContext())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateView(response: CurrentWeatherResponse) {
        tvTemp?.text = "${response.current?.temp_c}°C"
        tvCurrentCountry?.text = response.location?.name
        tvCondition?.text = response.current?.condition?.text
        ivCondition?.let { GlideUtil.setImage(it, "https:${response.current?.condition?.icon}") }
    }

}