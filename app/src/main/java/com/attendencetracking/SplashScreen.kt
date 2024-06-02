package com.attendencetracking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.attendencetracking.common.SessionManager

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val sessionManager = SessionManager(this)
        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            if(sessionManager.isLogedIn()){
                startActivity(Intent(this, DashboardScreen::class.java))
            }else {
                startActivity(Intent(this, LoginScreen::class.java))
            }
            finish()
        },1000)
    }
}