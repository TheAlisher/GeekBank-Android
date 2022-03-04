package com.alish.geekbank.presentation.ui.fragments.sign.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentSignInBinding


class SignIn : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentSignInBinding
    private lateinit var preferenceManager: PreferencesHelper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
    }

    private fun initialize() {
        preferenceManager = PreferencesHelper(requireContext())
    }

    private fun setupListeners() {
        binding.btnEnter.setOnClickListener {
            if (signDetails()){
                signIn()
            }
        }
    }

    private fun signIn(){
        viewModel.getData()?.get()?.addOnSuccessListener {
            for (i in it){
                if (binding.etLogin.text.toString() == i.data["id"] && binding.etPass.text.toString() == i.data["password"]){
                    showToast("uspeh")
                    preferenceManager.putBoolean("bool",true)
                    mainNavController().navigate(R.id.action_signFlowFragment_to_mainFlowFragment)
                    break
                }else{
                    showToast("proval")
                }
            }
        }

    }

    private fun signDetails():Boolean{
        if (binding.etLogin.text.toString().trim().isEmpty()){
            showToast("Enter Login")
            return false
        }else if (binding.etPass.text.toString().trim().isEmpty()){
            showToast("Enter Password")
            return false
        }else if (binding.etLogin.text.toString().trim().length < 8){
            showToast("Login must be bigger 7 simbols")
            return false
        }else if(binding.etPass.text.toString().trim().length < 8){
            showToast("Password must be bigger than 7 simbols")
            return false
        }
        else {
            return true
        }
    }

    private fun showToast(message: String){
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }


}