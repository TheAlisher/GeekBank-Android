package com.alish.geekbank.presentation.ui.fragments.sign

import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FlowFragmentSignBinding
import com.alish.geekbank.presentation.base.BaseFlowFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignFlowFragment :
    BaseFlowFragment(R.layout.flow_fragment_sign, R.id.nav_host_fragment_sign) {
    val binding: FlowFragmentSignBinding by viewBinding(FlowFragmentSignBinding::bind)

    @Inject
    lateinit var preferenceSign: PreferencesHelper

    override fun setupNavigation() {
        if (!preferenceSign.getOnBoardBoolean()) {
            navController.navigate(R.id.onBoardingFragment)
        } else {
            navController.navigate(R.id.signInFragment)
        }
    }
}