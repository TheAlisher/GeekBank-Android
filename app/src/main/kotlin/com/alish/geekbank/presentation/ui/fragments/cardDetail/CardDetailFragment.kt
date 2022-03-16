package com.alish.geekbank.presentation.ui.fragments.cardDetail

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentCardDetailBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.models.CardListUIModel
import com.alish.geekbank.presentation.models.CardModel
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.adapters.CardDetailAdapter
import com.alish.geekbank.presentation.ui.adapters.CardDetailListAdapter
import com.alish.geekbank.presentation.ui.fragments.freezeCard.FreezeDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CardDetailFragment :
    BaseFragment<CardDetailViewModel, FragmentCardDetailBinding>(R.layout.fragment_card_detail) {

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    override val viewModel: CardDetailViewModel by viewModels()
    override val binding by viewBinding(FragmentCardDetailBinding::bind)
    private val cardDetailAdapter = CardDetailAdapter()
    private val cardDetailListAdapter = CardDetailListAdapter()
    private var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null

    override fun initialize() = with(binding) {
        listRecycler.adapter = cardDetailAdapter
        listRecycler.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetInclude.recycler.adapter = cardDetailListAdapter
        bottomSheetInclude.recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun setupListeners() {
        setupDialog()
        setupAction()
        setupBottomSheet()
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetInclude.bottomSheet)
        bottomSheetBehavior?.peekHeight = resources.displayMetrics.heightPixels / 3
        bottomSheetBehavior?.isHideable = false
        bottomSheetBehavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                    else -> {}
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.bottomSheetInclude.imageBack.rotation = slideOffset * 180
            }
        })
    }

    private fun setupAction() = with(binding) {
        buttonHorizontal.setOnClickListener {
            findNavController().navigate(CardDetailFragmentDirections.actionCardDetailFragmentToTransferFragment())
        }
        buttonWallet.setOnClickListener {
            findNavController().navigate(CardDetailFragmentDirections.actionCardDetailFragmentToPaymentsFragment())
        }
        buttonExchange.setOnClickListener {
            findNavController().navigate(CardDetailFragmentDirections.actionCardDetailFragmentToExchangeFragment())
        }

        buttonQR.setOnClickListener {
//            findNavController().navigate(CardDetailFragmentDirections.actionCardDetailFragmentToScannerFragment())
        }
        buttonSettings.setOnClickListener {
            findNavController().navigate(CardDetailFragmentDirections.actionCardDetailFragmentToSettingsFragment())
        }
        binding.imageArrow.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun setupSubscribes() {
        viewModel.stateUser.collectUIState {
            when(it){
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    it.data.forEach {data->
                        if (data?.id == preferencesHelper.getString("id") ){
                            val list = ArrayList<CardModel>()
                            list.add(
                                CardModel(
                                    data?.firstCard?.get("cardNumber").toString(),
                                    data?.firstCard?.get("name").toString(),
                                    data?.firstCard?.get("date").toString(),
                                    data?.firstCard?.get("money").toString(),
                                )
                            )

                            list.add(
                                CardModel(
                                    data?.secondCard?.get("cardNumber").toString(),
                                    data?.secondCard?.get("name").toString(),
                                    data?.secondCard?.get("date").toString(),
                                    data?.secondCard?.get("money").toString(),
                                )
                            )

                            cardDetailAdapter.submitList(list)
                            val myPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    when (position) {
                                        0 -> {
                                            val list2: ArrayList<CardListUIModel> = ArrayList()
                                            list2.add(
                                                CardListUIModel(R.drawable.airbnb,
                                                    "Airbnb",
                                                    1))
                                            cardDetailListAdapter.submitList(list2)
                                        }

                                        1 -> {
                                            val list2: ArrayList<CardListUIModel> = ArrayList()
                                            list2.add(CardListUIModel(R.drawable.airbnb,
                                                data?.secondCard?.get("money").toString(),
                                                1))
                                            cardDetailListAdapter.submitList(list2)
                                        }
                                    }
                                }
                            }
                            binding.listRecycler.registerOnPageChangeCallback(myPageChangeCallback)
                        }
                    }
                }
            }
        }

    }

    private fun setupDialog() {
        binding.buttonFreezeCard.setOnClickListener {
            val dialog = FreezeDialogFragment()
            fragmentManager?.let { it1 -> dialog.show(it1,"freezeDialog") }
        }
    }
}