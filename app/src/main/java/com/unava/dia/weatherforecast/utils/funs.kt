package com.unava.dia.weatherforecast.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

fun <T : ViewModel> obtainViewModel(owner: ViewModelStoreOwner,
                                             viewModelClass: Class<T>,
                                             factory: ViewModelProvider.Factory
) =
    ViewModelProvider(owner, factory)[viewModelClass]
