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
 * After the animation ends, it navigates to the LoginActivity.
 */
class SplashActivity : AppCompatActivity() {

    // MediaPlayer instance used to play the splash sound effect
    private var mediaPlayer: MediaPlayer? = null

    /**
     * Called when the activity is first created.
     * Initializes the splash screen, plays sound, and starts the animation.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize and start the sound effect
        mediaPlayer = MediaPlayer.create(this, R.raw.start11)
        mediaPlayer?.start()

        // Find the logo ImageView in the layout
        val logo: ImageView = findViewById(R.id.logo)

        // Load and start the rotation + zoom animation
        val anim = AnimationUtils.loadAnimation(this, R.anim.rotate_zoom)
        logo.startAnimation(anim)

        // Listen for animation events
        anim.setAnimationListener(object : Animation.AnimationListener {

            // Called when the animation starts
            override fun onAnimationStart(animation: Animation) {}

            // Called when the animation ends
            override fun onAnimationEnd(animation: Animation) {
                // After animation ends, navigate to LoginActivity
                startActivity(Intent(this@SplashActivity, FirstOnboardingFragment::class.java))
                // Apply fade transition between activities
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                // Finish splash screen so user can’t go back to it
                finish()
            }

            // Called if the animation repeats (not used here)
            override fun onAnimationRepeat(animation: Animation) {}
        })


        val prefs = getSharedPreferences("NewslyPrefs", MODE_PRIVATE)
        val completed = prefs.getBoolean("onboarding_completed", false)

        if (completed) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
        finish()

    }

    /**
     * Called when the activity is destroyed.
     * Releases the MediaPlayer resource to prevent memory leaks.
     */
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
