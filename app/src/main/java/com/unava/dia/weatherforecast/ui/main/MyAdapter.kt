package com.unava.dia.weatherforecast.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.unava.dia.weatherforecast.ui.fragments.current.FragmentCurrent
import com.unava.dia.weatherforecast.ui.fragments.future.FragmentFuture
import com.unava.dia.weatherforecast.ui.fragments.hourly.HourlyFragment

@Suppress("DEPRECATION")
internal class MyAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentCurrent()
            }
            1 -> {
                FragmentFuture()
            }
            2 -> {
                HourlyFragment()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}