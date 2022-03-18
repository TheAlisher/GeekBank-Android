package com.alish.geekbank.presentation.ui.fragments.editProfile

import android.Manifest
import android.net.Uri
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentEditProfileBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.extensions.hasPermissionCheckAndRequest
import com.alish.geekbank.presentation.extensions.setImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<EditProfileViewModel, FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    override val viewModel: EditProfileViewModel by viewModels()
    override val binding by viewBinding(FragmentEditProfileBinding::bind)
    private var imageUri: Uri? = null
    private var image = ""

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