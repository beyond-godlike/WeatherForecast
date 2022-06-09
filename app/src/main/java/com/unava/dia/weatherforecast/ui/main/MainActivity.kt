package com.unava.dia.weatherforecast.ui.main

import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.unava.dia.weatherforecast.R
import com.unava.dia.weatherforecast.utils.UpdateService
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
		setTheme(R.style.Theme_WeatherForecast)
        setContentView(R.layout.activity_main)
        initUi()
        initService()
    }
    private fun initService() {
        if(!isServiceRunning<UpdateService>()) {
            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.SECOND, 10)

            val intent = Intent(this, UpdateService::class.java)
            val pintent = PendingIntent.getService(this, 0, intent, 0)
            val alarm = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            //for 60 mint 60*60*1000
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 60*60*1000, pintent)

            startService(intent)
        }
    }

    @Suppress("DEPRECATION") // Deprecated for third party Services.
    inline fun <reified T> Context.isServiceRunning() =
        (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == T::class.java.name }


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