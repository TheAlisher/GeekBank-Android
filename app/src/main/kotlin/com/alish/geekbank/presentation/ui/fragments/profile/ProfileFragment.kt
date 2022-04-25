package com.alish.geekbank.presentation.ui.fragments.profile

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.local.preferences.Localization
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentProfileBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.extensions.setImage
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.fragments.theme.ThemeDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<ProfileViewModel, FragmentProfileBinding>(R.layout.fragment_profile) {

    override val viewModel: ProfileViewModel by viewModels()
    override val binding by viewBinding(FragmentProfileBinding::bind)
    private var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null

    private var uri: Uri? = null


    @Inject
    lateinit var preferencesHelper: PreferencesHelper


    private fun setLocale(locale: Localization) {
        if (preferencesHelper.getLanguageCode() != locale.languageCode) {
            preferencesHelper.setLocale(locale)
            preferencesHelper.isChange = true
            activity?.recreate()
        }
    }

    override fun initialize() {
        bottomSheetBehavior =
            BottomSheetBehavior.from(binding.bottomSheet2Include.bottomSheetLanguage)
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        if (preferencesHelper.getString(Constants.USER_CONDITION) == "neAdmin") {
            binding.containerForAdmins.visibility = View.GONE
        }

    }

    override fun setupListeners() = with(binding) {
        containerLanguage.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }
        containerForAdmins.setOnClickListener {
            findNavController().navigate(R.id.adminFragment)
        }
        setupRussian()
        setupEnglish()
        setupDialogTheme()
        changePassClick()
        setupEditProfile()
        setupChangePinCode()
    }

    private fun setupChangePinCode() {
        binding.containerPinCode.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToFirstFragment(true)
            )
        }
    }

    private fun changePassClick() {
        binding.txtAccountPassword.setOnClickListener {
            findNavController().navigate(R.id.changePassword)
        }

    }

    override fun setupRequests() {
        viewModel.stateUser.collectUIState {
            binding.imagePlaceholder.isVisible = it !is UIState.Loading
            when (it) {
                is UIState.Error -> {

                }
                is UIState.Loading -> {
                }
                is UIState.Success -> {
                    binding.txtName.text = it.data?.name
                    lifecycleScope.launch {
                        viewModel.downloadProfileImage(preferencesHelper.userID.toString())
                            ?.let { image ->
                                binding.imagePlaceholder.setImage(
                                    image,
                                    binding.progressBarEdit
                                )
                            }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e("lifecycle", "start")
    }

    override fun onResume() {
        super.onResume()
        Log.e("lifecycle", "resume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("lifecycle", "pause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("lifecycle", "stop")
    }

    private fun setupRussian() = with(binding) {
        bottomSheet2Include.containerRussianBottomSheet.setOnClickListener {
            setLocale(Localization.RUSSIAN)
        }
    }

    private fun setupEnglish() {
        binding.bottomSheet2Include.containerEnglishBottomSheet.setOnClickListener {
            setLocale(Localization.ENGLISH)
        }
    }

    private fun setupDialogTheme() {
        binding.containerTheme.setOnClickListener {
            val dialog = ThemeDialogFragment()
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "theme") }
        }
    }

    private fun setupEditProfile() {
        binding.imageEdit.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }
    }

}
