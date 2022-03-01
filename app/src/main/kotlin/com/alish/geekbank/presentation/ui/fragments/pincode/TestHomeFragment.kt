package com.alish.geekbank.presentation.ui.fragments.pincode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alish.geekbank.databinding.FragmentTestHomeBinding

class TestHomeFragment : Fragment() {
    private lateinit var binding: FragmentTestHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTestHomeBinding.inflate(inflater)
        return binding.root
    }
}