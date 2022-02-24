package com.alish.geekbank.presentation.ui.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentHomeBinding
import kotlin.math.min


class HomeFragment : Fragment() {
    private var xCoOrdinate = 0f

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivFirst.setOnTouchListener(View.OnTouchListener { view, event ->
            val displayMetrics = resources.displayMetrics
            val cardWidth = binding.ivFirst.width
            val cardStart = (displayMetrics.widthPixels.toFloat() / 2 - (cardWidth / 2))


            when (event.actionMasked) {
                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX
                    if (newX - cardWidth < cardStart) {// or new

                        view.animate().x(
                            min(cardStart, newX - (cardWidth / 2))
                        )
                            .setDuration(150)
                            .start()
                    }
                }
                MotionEvent.ACTION_UP -> {

                    findNavController().navigate(R.id.action_homeFragment_to_cardFragment)

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