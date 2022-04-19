package com.alish.geekbank.presentation.ui.fragments.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnNextLayout
import androidx.core.view.isVisible
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FlowFragmentMainBinding
import com.alish.geekbank.presentation.base.BaseFlowFragment
import com.alish.geekbank.presentation.extensions.*
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
                R.id.create_card,
                R.id.cardDetailFragment,
                R.id.settingsFragment,
                R.id.paymentsFragment,
                R.id.adminFragment,
                R.id.createUserFragment,
                R.id.exchangeFragment,
                R.id.transferFragment,
                R.id.qrFragment,
                R.id.firstFragment,
                R.id.forgotPasswordDialogFragment,
                R.id.editProfileFragment,
                R.id.scannerFragment,
                -> {
                    whetherToShow(false)
                }
                R.id.profileFragment -> {
                    if (preferenceHelper.isShown()) {

                    }
                }
                else -> {
                    whetherToShow(true)
                }
            }

            binding.bottomNavigationView.background = null
            binding.bottomNavigationView.menu.getItem(2).isEnabled = false
        }
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun setUpListeners() {
        binding.fabView.doOnNextLayout {
            val colors = intArrayOf(
                Color.WHITE,
                Color.RED,
                Color.BLUE
            )

            val cornerRadius = 200f.dp
            val padding = 28.dp
            val centerX = it.width.toFloat() / 2 - padding
            val centerY = it.height.toFloat() / 2 - padding

            val shadowDrawable = createShadowDrawable(
                colors = colors,
                cornerRadius = cornerRadius,
                elevation = padding.toFloat(),
                centerX = centerX,
                centerY = centerY
            )
            val colorDrawable = createColorDrawable(
                backgroundColor = Color.DKGRAY,
                cornerRadius = cornerRadius
            )
            it.setColorShadowBackground(
                shadowDrawable = shadowDrawable,
                colorDrawable = colorDrawable,
                padding = 30.dp
            )
            val endColors = intArrayOf(
                Color.RED,
                Color.WHITE,
                Color.RED
            )

            animateShadow(
                shapeDrawable = shadowDrawable,
                startColors = colors,
                endColors = endColors,
                duration = 2000,
                centerX = centerX,
                centerY = centerY
            )
        }
    }

    private fun whetherToShow(b: Boolean) {
        binding.bottomAppBar.isVisible = b
        binding.fab.isVisible = b
        binding.fabView.isVisible = b
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        navController.navigate(R.id.firstFragment)
//        if (!preferenceHelper.isShown()) {
//            navController.navigate(R.id.pinCodeFragment)
//        } else if (preferenceHelper.isShown() && navController.currentDestination == navController.findDestination(
//                R.id.navigation_profile)
//        ) {
//            Toast.makeText(requireContext(), "Language changed", Toast.LENGTH_SHORT).show()
//        } else {
//            navController.navigate(R.id.inputPinCodeFragment)
//        }
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