package com.alish.geekbank.presentation.ui.fragments.transfer

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.databinding.FragmentTransferBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.extensions.overrideOnBackPressed
import com.alish.geekbank.presentation.models.CardModelUI
import com.alish.geekbank.presentation.models.TransferModel
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.adapters.CardTransferAdapter
import com.alish.geekbank.presentation.ui.adapters.viewtypeadapter.AdapterTransferViewType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class TransferFragment :
    BaseFragment<TransferViewModel, FragmentTransferBinding>(R.layout.fragment_transfer) {

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    private var cardNumber: String? = null
    private var fromCard = TransferModel()
    private var toCard = TransferModel()
    override val viewModel: TransferViewModel by viewModels()
    override val binding by viewBinding(FragmentTransferBinding::bind)
    private val adapterCard = CardTransferAdapter()
    private val adapterCardTo = AdapterTransferViewType(this::onInput)
    private val cardsList = ArrayList<CardModelUI?>()


    override fun initialize() = with(binding) {
        cardRecycler1.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )

        cardRecycler1.adapter = adapterCard
        PagerSnapHelper().attachToRecyclerView(cardRecycler1)
        PagerSnapHelper().attachToRecyclerView(cardRecycler2)
        cardRecycler2.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        cardRecycler2.adapter = adapterCardTo
    }

    override fun setupListeners() {
        checkPosition()
        sendListeners()
    }

    private fun sendListeners() = with(binding) {
        btnSetMoney.setOnClickListener {
            if (binding.inputTxtTransfer.text.toString() != "") {
                var money = inputTxtTransfer.text.toString()
                if (transferDetails(money.toInt())) {
                    var balanceFrom: Int = fromCard.money!! - money.toInt()
                    var balanceTo = toCard.money!! + money.toInt()

                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.updateAccount(balanceFrom, fromCard.cardNumber.toString())
                    }
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.updateAccount(balanceTo, toCard.cardNumber.toString())
                    }
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.addHistory(
                            money.toInt(),
                            fromCard.cardNumber,
                            toCard.cardNumber,
                            "minus",
                            getTime()
                        )
                    }
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.addHistory(
                            money.toInt(),
                            fromCard.cardNumber,
                            toCard.cardNumber,
                            "plus",
                            getTime()
                        )
                    }
                    findNavController().navigate(R.id.cardDetailFragment)
                }
            } else {
                binding.inputTxtTransfer.error = "Введите сумму"
            }
        }
    }

    private fun checkPosition() {
        binding.cardRecycler1.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val offset: Int = binding.cardRecycler1.computeHorizontalScrollOffset()
                var position: Float =
                    offset.toFloat() / (binding.cardRecycler1.getChildAt(0).measuredWidth).toFloat()
                position += 0.5f
                val postInt: Int = position.toInt()
                fromCard.cardNumber = adapterCard.currentList[postInt].cardNumber
                fromCard.money = adapterCard.currentList[postInt].money

            }
        })
        binding.cardRecycler2.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val offset: Int = binding.cardRecycler2.computeHorizontalScrollOffset()
                var position: Float =
                    offset.toFloat() / (binding.cardRecycler2.getChildAt(0).measuredWidth).toFloat()
                position += 0.5f
                val postInt: Int = position.toInt()
                if (postInt == 0) {
                    toCard.cardNumber = cardNumber
                } else {
                    toCard.cardNumber = adapterCard.currentList[postInt].cardNumber
                    toCard.money = adapterCard.currentList[postInt].money
                }
            }
        })
    }

    private fun onInput(number: String?) {
        cardNumber = number
    }

    override fun setupSubscribes() {
        viewModel.stateCard.collectUIState {
            when (it) {
                is UIState.Error -> {}
                is UIState.Loading -> {}
                is UIState.Success -> {
                    val list = ArrayList<CardModelUI?>()
                    list.addAll(it.data.filter { it?.blocked == false })
                    adapterCard.submitList(list)
                    val list2 = ArrayList<CardModelUI?>()
                    list2.addAll(it.data.filter { it?.blocked == false } )
                    list2.add(0,CardModelUI())
                    adapterCardTo.submitList(list2)

                }
            }
        }
    }

    private fun transferDetails(money: Int): Boolean {
        if (fromCard.cardNumber == toCard.cardNumber) {
            toast("Вы выбрали одинаковые карты")
            return false
        } else if (fromCard.money!! < money) {
            toast("На балансе недостаточно средств")
            return false
        } else {
            return true
        }
    }

    private fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun getTime(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val curentDate = sdf.format(Date())
        return curentDate

    }

}