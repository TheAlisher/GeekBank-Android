package com.alish.geekbank.presentation.ui.fragments.settings.cardlock

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentCardLockBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.adapters.CardLockAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardLockFragment(
) : BaseFragment<CardLockViewModel, FragmentCardLockBinding>(R.layout.fragment_card_lock) {

    override val viewModel: CardLockViewModel by viewModels()
    override val binding by viewBinding(FragmentCardLockBinding::bind)
    private val cardLockAdapter = CardLockAdapter(
        this::setOnItemClickListener
    )
    private var positionCard = ""

    override fun initialize() = with(binding) {
        recyclerCardLock.adapter = cardLockAdapter
        recyclerCardLock.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun setupListeners() {

    }

    override fun setupRequests() {
        viewModel.stateCard.collectUIState {
            when (it) {
                is UIState.Error -> {

                }
                is UIState.Loading -> {

                }
                is UIState.Success -> {
                    cardLockAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun setOnItemClickListener(pos: String) {
        findNavController().navigate(
            CardLockFragmentDirections.actionCardLockFragmentToFreezeDialogFragment(
                pos
            )
        )
    }

}