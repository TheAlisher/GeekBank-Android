package com.alish.geekbank.presentation.ui.fragments.editProfile

import android.Manifest
import android.net.Uri
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentEditProfileBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.extensions.setImage
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<EditProfileViewModel, FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    override val viewModel: EditProfileViewModel by viewModels()
    override val binding by viewBinding(FragmentEditProfileBinding::bind)
    private var imageUri: Uri? = null
    private var image = ""

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

    private fun checkPermissionGallery() {
//        if (hasPermissionCheckAndRequest(
//                requestPermissionLauncher,
//                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
//            )
//        ) {
//            fileChooserContract.launch("image/*")
        //     }
        fileChooserContract.launch("image/*")
    }

    override fun setupListeners() {
        clickGallery()
    }

    override fun setupRequests() {
        viewModel.stateUser.collectUIState {
            when (it) {
                is UIState.Error -> {
                }
                is UIState.Loading -> {
                }
                is UIState.Success -> {
                    it.data.forEach { data ->
                        if (data?.id == preferencesHelper.getString(Constants.USER_ID)) {
                            binding.inputName.setText(data?.name)
                            binding.inputLastName.setText(data?.surname)
                            binding.inputNumber.setText(data?.number)
                        }

                    }
                }
            }
        }
    }

    private fun clickGallery() {
        binding.txtChoosePhoto.setOnClickListener(clickListener)
    }

    private val clickListener = View.OnClickListener {
        checkPermissionGallery()
    }

    private val fileChooserContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                binding.imageEditPlaceholder.setImage(it.toString())
                imageUri = it
            }
        }
}