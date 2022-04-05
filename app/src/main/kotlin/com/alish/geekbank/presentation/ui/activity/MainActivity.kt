package com.alish.geekbank.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.alish.geekbank.R
import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.local.preferences.LocalHelper
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.security.Provider
import java.security.Security
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var isAuthorized = false

    @Inject
    lateinit var localHelper: LocalHelper

    @Inject
    lateinit var preferenceHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GeekBankAndroid)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        localHelper.loadLocale(this)
        isAuthorized = preferenceHelper.getBoolean(Constants.IS_AUTHORIZED)
        setUpNavigation()
    }
    

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val graph = navController.navInflater.inflate(R.navigation.nav_graph)

        when {
            !isAuthorized -> {
                graph.setStartDestination(R.id.signFlowFragment)
            }
            isAuthorized -> {
                graph.setStartDestination(R.id.mainFlowFragment)
            }
        }

        navController.graph = graph
    }
}