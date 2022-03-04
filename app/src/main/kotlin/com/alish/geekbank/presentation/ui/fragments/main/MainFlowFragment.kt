package com.alish.geekbank.presentation.ui.fragments.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FlowFragmentMainBinding
import com.alish.geekbank.presentation.base.BaseFlowFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFlowFragment : BaseFlowFragment(R.layout.flow_fragment_main,R.id.nav_host_fragment_main) {

    private val binding by viewBinding(FlowFragmentMainBinding::bind)
    private val mAppBarConfiguration: AppBarConfiguration? = null
    @Inject
    lateinit var preferenceHelper: PreferencesHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false
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
        val editor = getSharedPreferences("PASS_CODE", AppCompatActivity.MODE_PRIVATE).edit()
        editor.putBoolean("is_pass", false)
        editor.apply()
    }

}