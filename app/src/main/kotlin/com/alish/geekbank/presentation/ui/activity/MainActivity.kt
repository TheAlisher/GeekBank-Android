package com.alish.geekbank.presentation.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.fragment.NavHostFragment
import com.alish.geekbank.R
import com.alish.geekbank.databinding.ActivityMainBinding
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    @Inject
    lateinit var preferenceHelper: PreferencesHelper

    private val mAppBarConfiguration: AppBarConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigation()
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController

    }



    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        if (mAppBarConfiguration != null) {
            setupActionBarWithNavController(this, navController, mAppBarConfiguration)
        }

        if (!preferenceHelper.isShown()) {
            navController.navigate(R.id.pinCodeFragment)
        }else{
            navController.navigate(R.id.inputPinCodeFragment)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false
        setUpNavigation()

    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
    }

    override fun onStop() {
        super.onStop()
        val editor = getSharedPreferences("PASS_CODE", MODE_PRIVATE).edit()
        editor.putBoolean("is_pass", false)
        editor.apply()
    }

    private fun navigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
}