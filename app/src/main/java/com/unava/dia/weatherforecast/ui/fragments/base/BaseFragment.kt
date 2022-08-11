package com.unava.dia.weatherforecast.ui.fragments.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.unava.dia.weatherforecast.data.WeatherSharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment(private val cf: Int) : Fragment() {

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
}