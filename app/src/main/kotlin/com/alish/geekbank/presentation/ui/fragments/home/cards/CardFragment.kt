package com.alish.geekbank.presentation.ui.fragments.home.cards

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentCardBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.models.CardModelUI
import com.alish.geekbank.presentation.models.HistoryModelUI
import com.alish.geekbank.presentation.ui.adapters.CardDetailAdapter
import com.alish.geekbank.presentation.ui.adapters.CardDetailListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CardFragment : BaseFragment<CardViewModel, FragmentCardBinding>(R.layout.fragment_card) {

    override val viewModel: CardViewModel by viewModels()
    override val binding: FragmentCardBinding by viewBinding(FragmentCardBinding::bind)
    private val cardDetailAdapter = CardDetailAdapter()
    private val cardDetailListAdapter = CardDetailListAdapter()
    private var positionCard = ""
    val list = ArrayList<CardModelUI>()

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    override fun setupListeners() {
//        setupAction()
        checkPosition()
    }

    override fun initialize() = with(binding) {
        listRecyclerCard.adapter = cardDetailAdapter
        listRecyclerCard.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    private fun checkPosition() {
        binding.listRecyclerCard.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val offset: Int = binding.listRecyclerCard.computeHorizontalScrollOffset()
                var position: Float =
                    offset.toFloat() / (binding.listRecyclerCard.getChildAt(0).measuredWidth).toFloat()
                position += 0.5f
                val postInt: Int = position.toInt()
                positionCard = cardDetailAdapter.currentList[postInt].cardNumber.toString()
                val filteredList = ArrayList<HistoryModelUI?>()
                cardDetailListAdapter.submitList(filteredList.sortedByDescending { data -> data?.dateTime })



            }
        })
    }


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
//    private fun setupAction() = with(binding) {
//        ivExtraCard.setOnClickListener {
//            findNavController().navigate(CardFragmentDirections.actionCardFragmentToCardDetailFragment())
//        }
//        ivSecond.setOnClickListener {
//            findNavController().navigate(CardFragmentDirections.actionCardFragmentToCardDetailFragment())
//        }
//    }
