package com.alish.geekbank.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alish.geekbank.databinding.ItemTransferListBinding
import com.alish.geekbank.presentation.base.BaseComparatorCard
import com.alish.geekbank.presentation.models.CardModelUI

class CardTransferAdapter :
    ListAdapter<CardModelUI, CardTransferAdapter.CardTransferViewHolder>(
        BaseComparatorCard(
        )
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTransferViewHolder {
        return CardTransferViewHolder(
            ItemTransferListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardTransferViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class CardTransferViewHolder(
        private val binding: ItemTransferListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CardModelUI) {
            binding.cardNumber.text = item.cardNumber
            binding.cardMoney.text = item.money.toString()
        }
    }
}