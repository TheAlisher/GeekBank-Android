package com.alish.geekbank.presentation.ui.fragments.settings

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentSettingsBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.models.CardModelUI
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.adapters.CardSettingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<SettingsViewModel, FragmentSettingsBinding>(R.layout.fragment_settings) {

    override val viewModel: SettingsViewModel by viewModels()
    override val binding by viewBinding(FragmentSettingsBinding::bind)
    private val cardSettingAdapter = CardSettingAdapter()

    override fun initialize() = with(binding) {
        recyclerCardSettings.adapter = cardSettingAdapter
        recyclerCardSettings.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun setupListeners() = with(binding) {
        line2.setOnClickListener {
            findNavController().navigate(R.id.cardLockFragment)
        }
    }

    override fun setupRequests() {
        viewModel.stateCard.collectUIState {
            when (it) {
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    var list: ArrayList<CardModelUI> = ArrayList()
                    for (i in it.data) {
                        if (list.size < 3) {
                            if (i != null) {
                                list.add(i)
                            }
                        } else {
                            break
                        }
                    }
                    cardSettingAdapter.submitList(list)
                }
            }
        }
    }
}