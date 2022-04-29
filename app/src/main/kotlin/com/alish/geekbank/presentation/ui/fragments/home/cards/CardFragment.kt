package com.alish.geekbank.presentation.ui.fragments.home.cards

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentCardBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.extensions.overrideOnBackPressed
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.adapters.CardDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CardFragment : BaseFragment<CardViewModel, FragmentCardBinding>(R.layout.fragment_card) {

    override val viewModel: CardViewModel by viewModels()
    override val binding: FragmentCardBinding by viewBinding(FragmentCardBinding::bind)
    private val adapter = CardDetailAdapter(this::click)

    private fun click() {
        findNavController().navigate(R.id.cardDetailFragment)
    }


    @Inject
    lateinit var preferencesHelper: PreferencesHelper
    override fun initialize() {
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }
    override fun setupListeners() {

    }

    override fun setupRequests() {
        viewModel.stateUser.collectUIState {
            when(it){
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    adapter.submitList(it.data)
                }
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        overrideOnBackPressed { findNavController().navigate(R.id.homeFragment) }
    }

}
