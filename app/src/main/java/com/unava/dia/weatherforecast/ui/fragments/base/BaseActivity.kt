package com.unava.dia.weatherforecast.ui.fragments.base

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    abstract fun observeViewModel()
    abstract fun initUi()
}