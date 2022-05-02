package com.alish.geekbank.presentation.ui.adapters.viewtypeadapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alish.geekbank.R
import com.alish.geekbank.databinding.ItemListTransferNumberBinding
import com.alish.geekbank.databinding.ItemTransferListBinding
import com.alish.geekbank.presentation.base.BaseComparatorCard
import com.alish.geekbank.presentation.models.CardModelUI

class AdapterTransferViewType(
    val onInput: (number: String?) -> Unit
) :
    ListAdapter<CardModelUI, RecyclerView.ViewHolder>(BaseComparatorCard()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_list_transfer_number ->
                EnterCardViewHolder(
                    ItemListTransferNumberBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            R.layout.item_transfer_list -> {
                MyCardViewHolder(
                    ItemTransferListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                MyCardViewHolder(
                    ItemTransferListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_list_transfer_number ->
                (holder as EnterCardViewHolder).onBind(getItem(position) as CardModelUI)
            R.layout.item_transfer_list ->
                (holder as MyCardViewHolder).onBind(getItem(position) as CardModelUI)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> {
                R.layout.item_list_transfer_number
            }
            else -> {
                R.layout.item_transfer_list
            }
        }
    }

    inner class EnterCardViewHolder(private val binding: ItemListTransferNumberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.enterCardNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    onInput(
                        binding.enterCardNumber.text.toString()
                    )
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
        }

        fun onBind(cardModelUI: CardModelUI) {
            binding.apply {
                enterCardNumber.setText(cardModelUI.cardNumber ?: "")
            }
        }
    }

    inner class MyCardViewHolder(private val binding: ItemTransferListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CardModelUI) {
            binding.cardNumber.text = item.cardNumber
            binding.cardMoney.text = item.money.toString()
        }
    }
}