package com.alish.geekbank.presentation.ui.fragments.home.cards

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentCardBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.state.UIState
import com.google.android.material.animation.AnimationUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CardFragment : BaseFragment<CardViewModel,FragmentCardBinding>(R.layout.fragment_card) {

    override val viewModel: CardViewModel by viewModels()
    override val binding:FragmentCardBinding by viewBinding(FragmentCardBinding::bind)
    private var xCoOrdinate = 0f
    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    @SuppressLint("ClickableViewAccessibility")
    override fun setupListeners() {
        binding.ivSecond.setOnTouchListener(View.OnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    xCoOrdinate = view.x - event.rawX
                }
                MotionEvent.ACTION_MOVE -> {
                    view.animate().x(event.rawX + xCoOrdinate)
                        .setDuration(0)
                        .start()
                }
                MotionEvent.ACTION_UP -> {

                    findNavController().navigate(R.id.action_cardFragment_to_cardDetailFragment)

                    Log.e("anime", "onViewCreated: $xCoOrdinate")

                }

                else -> {
                    view.clearAnimation()
                    return@OnTouchListener false
                }
            }
            true
        })
    }

    override fun setupRequests() {
        viewModel.stateUser.collectUIState {
            when(it){
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    it.data.forEach { data->
                        if (data?.id == preferencesHelper.getString("id")){
                            binding.dataCard.text = data?.firstCard?.get("date").toString()
                            binding.nameCard.text = data?.firstCard?.get("name").toString()
                            binding.roomCard.text = data?.firstCard?.get("cardNumber").toString()
                        }
                    }
                }
            }
        }
    }



}
