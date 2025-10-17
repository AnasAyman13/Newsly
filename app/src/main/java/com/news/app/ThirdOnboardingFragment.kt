package com.news.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ThirdOnboardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding_third, container, false)
        val btnGetStarted = view.findViewById<TextView>(R.id.btnGetStarted)

        btnGetStarted.setOnClickListener {
            completeOnboarding()
        }

        return view
    }

    private fun completeOnboarding() {
        val sharedPref = requireActivity().getSharedPreferences("NewslyPrefs", 0)
        sharedPref.edit().putBoolean("onboarding_completed", true).apply()

        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)

        val nextActivity = if (isLoggedIn) {
            MainActivity::class.java
        } else {
            LoginActivity::class.java
        }

        val intent = Intent(requireActivity(), nextActivity)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}
