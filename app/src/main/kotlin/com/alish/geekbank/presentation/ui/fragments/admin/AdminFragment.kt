package com.alish.geekbank.presentation.ui.fragments.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentAdminBinding


class AdminFragment : Fragment(R.layout.fragment_admin) {

    private val binding by viewBinding(FragmentAdminBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickButtons()
    }
    private fun clickButtons() {
        binding.createUserBtn.setOnClickListener {
            findNavController().navigate(R.id.createUserFragment)
        }
        binding.createCard.setOnClickListener {
            findNavController().navigate(R.id.createCardFragment)
        }
    }

}