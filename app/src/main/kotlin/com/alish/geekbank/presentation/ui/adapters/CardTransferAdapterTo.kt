package com.alish.geekbank.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alish.geekbank.R
import com.alish.geekbank.databinding.ItemListTransferNumberBinding
import com.alish.geekbank.databinding.ItemTransferListBinding
import com.alish.geekbank.presentation.base.BaseComparatorCard
import com.alish.geekbank.presentation.models.CardModelUI


class CardTransferAdapterTo :
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
                getItem(position).let {
                    (holder as EnterCardViewHolder).onBind(it)
                }

            R.layout.item_transfer_list ->
                getItem(position).let {
                    (holder as MyCardViewHolder).onBind(it)
                }

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
        fun onBind(cardModelUI: CardModelUI) {

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