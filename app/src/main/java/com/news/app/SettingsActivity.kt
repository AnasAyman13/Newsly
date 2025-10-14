package com.news.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.news.app.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = getSharedPreferences("countryPrefs", MODE_PRIVATE)
        val savedCountry = prefs.getString("country", null)

        if (savedCountry == null) {
            binding.usBtn.isChecked = true
        } else {

            when (savedCountry) {
                "us" -> binding.usBtn.isChecked = true
                "de" -> binding.grBtn.isChecked = true
                "uk" -> binding.ukBtn.isChecked = true
            }
        }
        binding.saveBtn.setOnClickListener {
            val choice = binding.group.checkedRadioButtonId
            val selectedCountry = when (choice) {
                R.id.us_btn -> "us"
                R.id.gr_btn -> "de"
                R.id.uk_btn -> "uk"
                else -> "us"
            }

            prefs.edit().putString("country", selectedCountry).apply()
            Toast.makeText(this, "Country Changed!", Toast.LENGTH_SHORT).show()
            onBackPressedDispatcher.onBackPressed()
        }
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

}




