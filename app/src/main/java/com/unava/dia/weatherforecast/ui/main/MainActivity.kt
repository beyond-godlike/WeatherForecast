package com.unava.dia.weatherforecast.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.tabs.TabLayout
import com.unava.dia.weatherforecast.R
import com.unava.dia.weatherforecast.utils.WeatherWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_WeatherForecast)
        setContentView(R.layout.activity_main)
        initUi()
        startWorkManager()
    }

    private fun startWorkManager() {
        val wm = WorkManager.getInstance(this)
        wm.enqueueUniquePeriodicWork(
            "updateWeather",
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequest
                .Builder(WeatherWorker::class.java, 30L, TimeUnit.MINUTES)
                .build())
    }

    private fun initUi() {
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        tabLayout.addTab(tabLayout.newTab().setText("Current"))
        tabLayout.addTab(tabLayout.newTab().setText("Forecast"))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MyAdapter(this, supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

}