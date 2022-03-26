package com.unava.dia.weatherforecast.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.unava.dia.weatherforecast.ui.main.MainActivity

import android.content.Intent
import android.os.Handler
import com.unava.dia.weatherforecast.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        GlobalScope.launch {
            delay(3000)
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
            // close this activity
            finish()
        }
    }
}