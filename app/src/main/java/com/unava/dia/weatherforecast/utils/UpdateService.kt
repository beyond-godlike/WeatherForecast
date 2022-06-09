package com.unava.dia.weatherforecast.utils

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import com.unava.dia.weatherforecast.data.Communicator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class UpdateService : Service() {
    @Inject
    lateinit var communicator: Communicator

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        Toast.makeText(applicationContext, "Service Created", Toast.LENGTH_SHORT).show()

        super.onCreate()
    }


    override fun onDestroy() {
        Toast.makeText(applicationContext, "Service Destroy", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(applicationContext, "Service Running ", Toast.LENGTH_SHORT).show()

        communicator.getCurrentWeather()
        communicator.getFutureWeather()

        return super.onStartCommand(intent, flags, startId)
    }
}