package com.alish.geekbank.presentation.ui.fragments.cardDetail

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentCardDetailBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.ui.adapters.CardDetailAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailFragment :
    BaseFragment<CardDetailViewModel, FragmentCardDetailBinding>(R.layout.fragment_card_detail) {

    override val viewModel: CardDetailViewModel by viewModels()
    override val binding by viewBinding(FragmentCardDetailBinding::bind)
    private val cardDetailAdapter = CardDetailAdapter()

    override fun initialize() = with(binding) {
        listRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        listRecycler.adapter = cardDetailAdapter

    }

    override fun setupListeners() {
        setupAlertDialog()
        setupAction()
        setupBottomSheet()
    }

    private fun setupBottomSheet() {
        BottomSheetBehavior.from(binding.sheetContainer)
        binding.fresh.setOnRefreshListener {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                binding.fresh.isRefreshing = false
            }, 2000)
        }
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

    }

    override fun setupRequests() {
        var list: ArrayList<Int> = ArrayList()
        list.add(R.drawable.visa)
        list.add(R.drawable.visa_card)
        cardDetailAdapter.addImage(list)
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

//    override fun setupSubscribes() {
//        viewModel.fetchCardDetail()
//        viewModel.cardDetailState.collectUIState {
//            when(it){
//                is UIState.Error -> {
//
//                }
//                is UIState.Loading -> {
//
//                }
//                is UIState.Success -> {
//                    cardDetailAdapter.submitList(it.data)
//                }
//            }
//        }
//    }
}