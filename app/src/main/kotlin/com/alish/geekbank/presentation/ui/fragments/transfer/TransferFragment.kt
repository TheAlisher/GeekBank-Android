package com.alish.geekbank.presentation.ui.fragments.transfer

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentTransferBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.models.CardModelUI
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.adapters.CardTransferAdapter
import com.alish.geekbank.presentation.ui.adapters.fingerparint.CardFingerprint
import com.alish.geekbank.presentation.ui.adapters.fingerparint.EnterCardNumberFingerPrint
import com.alish.geekbank.presentation.ui.adapters.fingerparint.TransferAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TransferFragment :
    BaseFragment<TransferViewModel, FragmentTransferBinding>(R.layout.fragment_transfer) {

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    private var moneyCurrent: String? = null
    private var fromCard = TransferModel()
    private var toCard = TransferModel()
    override val viewModel: TransferViewModel by viewModels()
    override val binding by viewBinding(FragmentTransferBinding::bind)
    private val adapterCard = CardTransferAdapter()
    private val adapterCardTo = CardTransferAdapterTo()


    private val fingerAdapter1 = TransferAdapter(listOf(CardFingerprint()))
    private val fingerAdapter2 = TransferAdapter(listOf(EnterCardNumberFingerPrint()))
    private val concatAdapter = ConcatAdapter(
        ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build(),
        fingerAdapter2,
        fingerAdapter1
    )

    override fun initialize() = with(binding) {
        cardRecycler1.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        cardRecycler1.adapter = fingerAdapter1
        PagerSnapHelper().attachToRecyclerView(cardRecycler1)
        cardRecycler2.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        cardRecycler2.adapter = concatAdapter
        fingerAdapter2.itemCount
        PagerSnapHelper().attachToRecyclerView(cardRecycler2)
        cardRecycler1.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        cardRecycler1.adapter = adapterCard
        PagerSnapHelper().attachToRecyclerView(cardRecycler1)
        PagerSnapHelper().attachToRecyclerView(cardRecycler2)
        cardRecycler2.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        cardRecycler2.adapter = adapterCardTo
    }

    override fun setupListeners() {
        checkPosition()
        sendListeners()

    }

    private fun sendListeners() = with(binding) {
        btnSetMoney.setOnClickListener {
            val money = inputTxtTransfer.text.toString()
            val changedMoney: Int = moneyCurrent!!.toInt() - money.toInt()
            lifecycleScope.launch {
                viewModel.updateAccount(
                    changedMoney, "1111222233334444").toString()
            }
            Toast.makeText(requireContext(), "$changedMoney", Toast.LENGTH_SHORT).show()
        }
    }

    override fun setupSubscribes() {
        viewModel.stateCard.collectUIState {
            when (it) {
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    val list = ArrayList<CardModelUI>()
                    Log.d("anime", "setupSubscribes: ${it.data}")
                    it.data.forEach { data ->
                        if (data?.id == preferencesHelper.getString(Constants.USER_ID)) with(binding) {
                            list.add(data!!)
                            cardRecycler1.postDelayed({
                                fingerAdapter1.submitList(list)
                            }, 300L)
                            fingerAdapter2.submitList(list)
                            Log.d("anime", "setupSubscribes: $list")
                            if (moneyCurrent == null)
                                moneyCurrent = data.money.toString()
                        }
                    }
                }
            }
        }
    }

    override fun checkPosition() = with(binding) {
        binding.cardRecycler1.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val offset: Int = binding.cardRecycler1.computeHorizontalScrollOffset()
                var position: Float =
                    offset.toFloat() / (binding.cardRecycler1.getChildAt(0).measuredWidth).toFloat()
                position += 0.5f
                val postInt: Int = position.toInt()
                val positionIndex = postInt + 1

                if (positionIndex == 0) {
                    txtNumberAvailable.text = fingerAdapter1.currentList[postInt].money?.toString()
                } else {
                    txtNumberAvailable.text = fingerAdapter1.currentList[postInt].money?.toString()
                }
            }
        })
    }
}