package com.alish.geekbank.presentation.ui.fragments.cardDetail

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentCardDetailBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.models.CardListUIModel
import com.alish.geekbank.presentation.models.CardsUIModel
import com.alish.geekbank.presentation.ui.adapters.CardDetailAdapter
import com.alish.geekbank.presentation.ui.adapters.CardDetailListAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailFragment :
    BaseFragment<CardDetailViewModel, FragmentCardDetailBinding>(R.layout.fragment_card_detail) {

    override val viewModel: CardDetailViewModel by viewModels()
    override val binding by viewBinding(FragmentCardDetailBinding::bind)
    private val cardDetailAdapter = CardDetailAdapter()
    private val cardDetailListAdapter = CardDetailListAdapter()
    private var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null

    override fun initialize() = with(binding) {
        listRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        listRecycler.adapter = cardDetailAdapter
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetInclude.recycler.adapter = cardDetailListAdapter
        bottomSheetInclude.recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun setupListeners() {
        setupAlertDialog()
        setupAction()
        setupBottomSheet()
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetInclude.bottomSheet)
        bottomSheetBehavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
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
            findNavController().navigate(CardDetailFragmentDirections.actionCardDetailFragmentToQrFragment())
        }
        buttonSettings.setOnClickListener {
            findNavController().navigate(CardDetailFragmentDirections.actionCardDetailFragmentToSettingsFragment())
        }
        binding.imageArrow.setOnClickListener {
            findNavController().navigate(CardDetailFragmentDirections.actionCardDetailFragmentToCardFragment())
        }

    }

    override fun setupSubscribes() {
        val list: ArrayList<CardsUIModel> = ArrayList()
        list.add(CardsUIModel(R.drawable.visa, "Visa", 1))
        list.add(CardsUIModel(R.drawable.visa_card, "Visa Card", 2))
        cardDetailAdapter.submitList(list)
        val list2: ArrayList<CardListUIModel> = ArrayList()
        list2.add(CardListUIModel(R.drawable.airbnb, "Airbnb", 1))
        cardDetailListAdapter.submitList(list2)
    }

    private fun setupAlertDialog() {
        binding.buttonFreezeCard.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setMessage("Are you sure,you want to freeze cards?")
            dialog.setPositiveButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            dialog.setNegativeButton("Freeze card",
                DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                })
            dialog.create()
            dialog.show()
        }
    }
}