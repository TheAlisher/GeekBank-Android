package com.alish.geekbank.presentation.ui.fragments.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingFragment : Fragment(R.layout.activity_onboarding) {

    private val binding by viewBinding(ActivityOnboardingBinding::bind)


    @Inject
    lateinit var preferenceOnBoard: PreferencesHelper


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

    }

    private fun initListeners() = with(binding) {
        buttonGetStarted.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
            preferenceOnBoard.putOnBoardBoolean()
        }
    }
}