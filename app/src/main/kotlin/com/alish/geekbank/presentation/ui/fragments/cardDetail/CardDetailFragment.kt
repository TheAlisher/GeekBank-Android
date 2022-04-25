package com.alish.geekbank.presentation.ui.fragments.cardDetail

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentCardDetailBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.extensions.setAnimation
import com.alish.geekbank.presentation.extensions.overrideOnBackPressed
import com.alish.geekbank.presentation.extensions.showToastShort
import com.alish.geekbank.presentation.models.CardModelUI
import com.alish.geekbank.presentation.models.HistoryModelUI
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

    private var navOptions: NavOptions.Builder? = null

    override val viewModel: CardDetailViewModel by viewModels()
    override val binding by viewBinding(FragmentCardDetailBinding::bind)
    private val cardDetailAdapter = CardDetailAdapter()
    private val cardDetailListAdapter = CardDetailListAdapter()
    private var positionCard = ""
    val list = ArrayList<CardModelUI>()

    val historyList = ArrayList<HistoryModelUI?>()
    private var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null

    override fun initialize() = with(binding) {
        listRecycler.adapter = cardDetailAdapter
        listRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        PagerSnapHelper().attachToRecyclerView(listRecycler)
        bottomSheetInclude.recycler.adapter = cardDetailListAdapter
        bottomSheetInclude.recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun setupListeners() {
        setupDialog()
        setupAction()
        setupBottomSheet()
        checkPosition()
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
            findNavController().navigate(
                R.id.transferFragment, null, NavOptions.Builder().setAnimation(
                    R.anim.input_method_enter,
                    R.anim.input_method_exit
                )
            )
        }
        buttonWallet.setOnClickListener {
            findNavController().navigate(
                R.id.paymentsFragment, null, NavOptions.Builder().setAnimation(
                    R.anim.fade_in,
                    R.anim.fade_out
                )
            )
        }
        buttonExchange.setOnClickListener {
            findNavController().navigate(
                R.id.exchangeFragment,
                null, NavOptions.Builder().setAnimation(
                    R.anim.dialog_enter,
                    R.anim.dialog_exit
                )
            )
        }
        buttonQR.setOnClickListener {
            findNavController().navigate(R.id.scannerFragment)
        }
        buttonSettings.setOnClickListener {
            findNavController().navigate(
                R.id.settingsFragment,
                null, NavOptions.Builder().setAnimation(
                    R.anim.dialog_enter,
                    R.anim.dialog_exit
                )
            )
        }
        binding.imageArrow.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun setupSubscribes() {
        viewModel.stateCard.collectUIState {
            when (it) {
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    cardDetailAdapter.submitList(it.data)
                }
            }
        }
        viewModel.stateHistory.collectUIState {
            when(it){
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    if (cardDetailListAdapter.currentList.size == 0 ){
                    historyList.addAll(it.data)
                    val filteredList = ArrayList<HistoryModelUI>()
                    historyList.forEach {
                        if ((positionCard == it?.fromCard && (it.condition == "minus" || it.condition == "service")) || (positionCard == it?.toCard && it.condition == "plus")) {
                            filteredList.add(it)

                        }
                    }
                    cardDetailListAdapter.submitList(filteredList.sortedByDescending { data -> data.dateTime })

                }
                }
            }
        }
    }

    private fun checkPosition() {
        binding.listRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val offset: Int = binding.listRecycler.computeHorizontalScrollOffset()
                var position: Float = offset.toFloat() / (binding.listRecycler.getChildAt(0).measuredWidth).toFloat()
                position += 0.5f
                val postInt: Int = position.toInt()
                positionCard = cardDetailAdapter.currentList[postInt].cardNumber.toString()
                val filteredList = ArrayList<HistoryModelUI?>()
                    historyList.forEach {
                        if ((positionCard == it?.fromCard && (it.condition == "minus" || it.condition == "service")) || (positionCard == it?.toCard && it.condition == "plus")){
                            filteredList.add(it)
                        }
                    }
                cardDetailListAdapter.submitList(filteredList.sortedByDescending { data -> data?.dateTime })



            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        overrideOnBackPressed { findNavController().navigate(R.id.cardFragment) }
    }

    private fun setupDialog() {
        binding.buttonFreezeCard.setOnClickListener {
            if (positionCard != ""){
                findNavController().navigate(CardDetailFragmentDirections.actionCardDetailFragmentToFreezeDialogFragment(positionCard))
        }else{
            this.showToastShort("У вас нет карт")
            }
    }}




}