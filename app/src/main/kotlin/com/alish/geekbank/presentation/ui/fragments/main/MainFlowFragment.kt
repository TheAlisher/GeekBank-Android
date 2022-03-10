package com.alish.geekbank.presentation.ui.fragments.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FlowFragmentMainBinding
import com.alish.geekbank.presentation.base.BaseFlowFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFlowFragment :
    BaseFlowFragment(R.layout.flow_fragment_main, R.id.nav_host_fragment_main) {
    val binding: FlowFragmentMainBinding by viewBinding(FlowFragmentMainBinding::bind)

    @Inject
    lateinit var preferenceHelper: PreferencesHelper


    override fun setupNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.pinCodeFragment,
                R.id.inputPinCodeFragment,
                R.id.cardDetailFragment,
                R.id.settingsFragment,
                R.id.paymentsFragment,
                R.id.profileFragment,
                R.id.exchangeFragment,
                R.id.transferFragment,
                R.id.qrFragment,
                -> {
                    whetherToShow(false)
                }
                else -> {
                    whetherToShow(true)
                }


            }
            binding.bottomNavigationView.background = null
            binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        }

    }


    private fun whetherToShow(b: Boolean) {
        binding.bottomAppBar.isVisible = b
        binding.fab.isVisible = b


    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()


        if (!preferenceHelper.isShown()) {
            navController.navigate(R.id.pinCodeFragment)
        } else {
            navController.navigate(R.id.inputPinCodeFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        val editor =
            this.requireActivity().getSharedPreferences("PASS_CODE", AppCompatActivity.MODE_PRIVATE)
                .edit()
        editor.putBoolean("is_pass", false)
        editor.apply()
    }
}