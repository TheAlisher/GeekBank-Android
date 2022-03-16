package com.alish.geekbank.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alish.geekbank.databinding.ItemExchangeBinding
import com.alish.geekbank.presentation.base.BaseComparatorExchange
import com.alish.geekbank.presentation.models.exchange.ExchangeModelsUI

class ExchangeAdapter :
    ListAdapter<ExchangeModelsUI, ExchangeAdapter.ExchangeViewHolder>(BaseComparatorExchange()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeViewHolder {
        return ExchangeViewHolder(
            ItemExchangeBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ExchangeViewHolder(private val binding: ItemExchangeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ExchangeModelsUI) {
            binding.exchange.text = item.exchange
            binding.exchangeName.text = item.exchangeName
        }
    }
}
