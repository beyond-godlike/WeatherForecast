package com.unava.dia.weatherforecast.utils

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun <T : ViewModel> obtainViewModel(owner: ViewModelStoreOwner,
                                             viewModelClass: Class<T>,
                                             factory: ViewModelProvider.Factory
) =
    ViewModelProvider(owner, factory)[viewModelClass]

private val sdf = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)

fun getDateString(time: Long, sdf: SimpleDateFormat) : String = sdf.format(time * 1000L)
fun getDateString(time: Long) : String = sdf.format(time * 1000L)

fun countRGBStroke(avg: Float): Int {
    val hsv = FloatArray(3)
    hsv[0] = 359.0f - (200.0f + (fib(avg.toInt()) / 100.0f ))
    hsv[1] = abs(avg) / 100
    hsv[2] = 0.8f

    return Color.HSVToColor(hsv)
}

fun countRGB(avg: Float): Int {
    val hsv = FloatArray(3)
    hsv[0] = 359.0f - (200.0f + (fib(avg.toInt()) / 100.0f ))
    hsv[1] = abs(avg) / 100
    hsv[2] = 1.0f

    return Color.HSVToColor(hsv)
}

private fun fib(n: Int): Int {
    return if (n <= 1) n else fib(n - 1) + fib(n - 2)
}

fun showError(msg: String, context: Context) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

