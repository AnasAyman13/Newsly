package com.news.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mediaPlayer = MediaPlayer.create(this, R.raw.start11)
        mediaPlayer?.start()

        sharedPref = getSharedPreferences("NewslyPrefs", Context.MODE_PRIVATE)  // Prefs for onboarding state

        val logo: ImageView = findViewById(R.id.logo)
        val anim = AnimationUtils.loadAnimation(this, R.anim.rotate_zoom)
        logo.startAnimation(anim)

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                val onboardingCompleted = sharedPref.getBoolean("onboarding_completed", false)
                val nextIntent: Intent = if (onboardingCompleted) {
                    // Skip to Main if already completed
                    Intent(this@SplashActivity, MainActivity::class.java)
                } else {
                    // First time: Go to Onboarding
                    Intent(this@SplashActivity, OnboardingActivity::class.java)
                }
                nextIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  // Clear stack for fresh start
                startActivity(nextIntent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
