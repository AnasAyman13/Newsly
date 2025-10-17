package com.news.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class SecondOnboardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding_second, container, false)

        val btnNext = view.findViewById<TextView>(R.id.btnNext)
        val tvSkip = view.findViewById<TextView>(R.id.tvSkip)

        btnNext.setOnClickListener {
            navigateToThirdScreen()
        }

        tvSkip.setOnClickListener {
            skipOnboarding()
        }

        return view
    }

    private fun navigateToThirdScreen() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.fragmentContainer, ThirdOnboardingFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun skipOnboarding() {
        // Save that onboarding is completed
        val sharedPref = requireActivity().getSharedPreferences("NewslyPrefs", 0)
        sharedPref.edit().putBoolean("onboarding_completed", true).apply()

        // Navigate to NewsActivity
        val intent = Intent(requireActivity(), Signup::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}