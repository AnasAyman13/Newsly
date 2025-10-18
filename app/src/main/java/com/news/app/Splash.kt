package com.news.app

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

/**
 * SplashActivity
 *
 * This activity serves as the splash screen of the app.
 * It displays the app logo with an animation and plays a short sound.
 * After the animation ends, it checks whether onboarding was completed
 * and navigates accordingly to either OnboardingActivity or MainActivity.
 */
class SplashActivity : AppCompatActivity() {

    // MediaPlayer instance used to play the splash sound effect
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize and play the splash sound
        mediaPlayer = MediaPlayer.create(this, R.raw.start11)
        mediaPlayer?.start()

        // Find the logo ImageView and start animation
        val logo: ImageView = findViewById(R.id.logo)
        val anim = AnimationUtils.loadAnimation(this, R.anim.rotate_zoom)
        logo.startAnimation(anim)

        // Check if onboarding was already completed
        val prefs = getSharedPreferences("NewslyPrefs", MODE_PRIVATE)
        val completed = prefs.getBoolean("onboarding_completed", false)

        // Animation listener to handle navigation after animation ends
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                val nextActivity = if (completed) {
                    MainActivity::class.java   // go to home screen
                } else {
                    OnboardingActivity::class.java  // go to onboarding
                }

                startActivity(Intent(this@SplashActivity, nextActivity))
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
