package com.alish.geekbank.presentation.ui.fragments.home.cards

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentCardBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.fragments.cardDetail.CardDetailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CardFragment : BaseFragment<CardViewModel, FragmentCardBinding>(R.layout.fragment_card) {

    override val viewModel: CardViewModel by viewModels()
    override val binding: FragmentCardBinding by viewBinding(FragmentCardBinding::bind)

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    override fun setupListeners() {
        setupAction()
    }



//    override fun setupRequests() {
//        viewModel.stateUser.collectUIState {
//            when (it) {
//                is UIState.Error -> {}
//                is UIState.Loading -> {}
//                is UIState.Success -> {
//                    it.data.forEach { data ->
//                        if (data?.id == preferencesHelper.getString("id")) {
//                            binding.dataCard.text = data?.firstCard?.get("date").toString()
//                            binding.nameCard.text = data?.firstCard?.get("name").toString()
//                            binding.roomCard.text = data?.firstCard?.get("cardNumber").toString()
//                        }
//                    }
//                }
//            }
//        }
//    }
    private fun setupAction() = with(binding) {
        ivExtraCard.setOnClickListener {
            findNavController().navigate(CardFragmentDirections.actionCardFragmentToCardDetailFragment())
        }
    ivSecond.setOnClickListener {
        findNavController().navigate(CardFragmentDirections.actionCardFragmentToCardDetailFragment())
    }
    }

}
