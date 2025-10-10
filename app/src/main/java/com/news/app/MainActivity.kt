package com.news.app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.news.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // Check onboarding
        val sharedPref = getSharedPreferences("NewslyPrefs", MODE_PRIVATE)
        val onboardingCompleted = sharedPref.getBoolean("onboarding_completed", false)
        if (!onboardingCompleted) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)

        // Setup Navigation
        val navController = findNavController(R.id.host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.favoritesFragment)
        )
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        // HOME button
        binding.homeImg.setOnClickListener {
            animateIcon(binding.homeImg)
            binding.homeImg.setColorFilter(ContextCompat.getColor(this, R.color.Blue))
            binding.favoriteImage.clearColorFilter()
            navController.navigate(R.id.homeFragment)
        }

        // FAVORITE button
        binding.favoriteImage.setOnClickListener {
            animateIcon(binding.favoriteImage)
            binding.favoriteImage.setColorFilter(ContextCompat.getColor(this, R.color.Blue))
            binding.homeImg.clearColorFilter()
            navController.navigate(R.id.favoritesFragment)
        }
    }

    private fun animateIcon(view: android.view.View) {
        view.animate().scaleX(1.2f).scaleY(1.2f).setDuration(150).withEndAction {
            view.animate().scaleX(1f).scaleY(1f).duration = 150
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                // فتح صفحة الإعدادات
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            R.id.action_logout -> {
                // تسجيل الخروج
                val sharedPref = getSharedPreferences("NewslyPrefs", MODE_PRIVATE)
                sharedPref.edit().putBoolean("onboarding_completed", false).apply()
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
