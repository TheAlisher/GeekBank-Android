package com.alish.geekbank.presentation.ui.fragments.profile

import android.Manifest
import android.net.Uri
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
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

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        for (permission in isGranted) {
            when {
                permission.value -> fileChooserContract.launch("image/*")
                !shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
//                    permissionMessage()
                }
            }
        }
    }

    private fun setLocale(locale: Localization) {
        if (preferencesHelper.getLanguageCode() != locale.languageCode) {
            preferencesHelper.setLocale(locale)
            activity?.recreate()
            preferencesHelper.isShown()
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
        changePassClick()
        setupEditProfile()
        clickImage()
        setupTheme()
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
            when (it) {
                is UIState.Error -> {

                }
                is UIState.Loading -> {

                }
                is UIState.Success -> {

                    binding.txtName.text = it.data?.name


                }
            }
        }
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

    private fun setupTheme() = with(binding) {
        containerTheme.setOnClickListener {
            chooseThemeDialog()
        }
    }

    private fun chooseThemeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.choose_theme_text))
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = preferencesHelper.darkMode

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->
            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    preferencesHelper.darkMode = 0
//                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    preferencesHelper.darkMode = 1
//                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    preferencesHelper.darkMode = 2
//                    delegate.applyDayNight()
                    dialog.dismiss()
                }
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun setupEditProfile() {
        binding.imageEdit.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }
    }

    private fun clickImage() {
        binding.imagePlaceholder.setOnClickListener {
//            if (hasPermissionCheckAndRequest(
//                    requestPermissionLauncher,
//                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
//                )
//            ) {
//                fileChooserContract.launch("image/*")
//            }
            fileChooserContract.launch("image/*")
        }
    }

    private val fileChooserContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            if (imageUri != null) {
                binding.imagePlaceholder.setImage(imageUri.toString())
                uri = imageUri
            }
        }
}
