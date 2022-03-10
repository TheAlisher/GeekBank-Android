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
import androidx.navigation.fragment.findNavController
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentCardBinding
import com.google.android.material.animation.AnimationUtils


class CardFragment : Fragment() {

    private lateinit var binding:FragmentCardBinding
    private var xCoOrdinate = 0f
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardBinding.inflate(inflater, container,false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
}
