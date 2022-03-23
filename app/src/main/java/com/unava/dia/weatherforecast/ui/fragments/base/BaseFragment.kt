package com.unava.dia.weatherforecast.ui.fragments.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.unava.dia.weatherforecast.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment(private val cf: Int) : Fragment() {
    lateinit var shared : SharedPreferences

    abstract fun bindViewModel()
    abstract fun observeViewModel()
    abstract fun initUi()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(cf, container, false)
    }

    fun showError(message: String, appContext: Context) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
    }

    fun getCityFromShared() : String {
        shared = requireActivity().getSharedPreferences(Constants.CITY, Context.MODE_PRIVATE)
        return shared.getString(Constants.CITY, "London" ).toString()
    }

    fun getCurrentIdFromShared() : Long {
        shared = requireActivity().getSharedPreferences(Constants.ID_CURRENT, Context.MODE_PRIVATE)
        return shared.getLong(Constants.ID_CURRENT, 0L)
    }

    fun getFutureIdFromShared() : Long {
        shared = requireActivity().getSharedPreferences(Constants.ID_FORECAST, Context.MODE_PRIVATE)
        return shared.getLong(Constants.ID_FORECAST, 0L)
    }

    fun saveForecastToShared(id: Long){
        shared = requireActivity().getSharedPreferences(Constants.ID_FORECAST, Context.MODE_PRIVATE)
        shared.edit().putLong(Constants.ID_FORECAST, id).apply()
    }

    fun saveCurrentToShared(id: Long){
        shared = requireActivity().getSharedPreferences(Constants.ID_CURRENT, Context.MODE_PRIVATE)
        shared.edit().putLong(Constants.ID_CURRENT, id).apply()
    }

    fun saveCountryToShared(country: String) {
        shared = requireActivity().getSharedPreferences(Constants.CITY, Context.MODE_PRIVATE)
        shared.edit().putString(Constants.CITY, country).apply()
    }
}