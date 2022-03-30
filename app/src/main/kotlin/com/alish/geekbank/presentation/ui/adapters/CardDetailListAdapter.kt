package com.alish.geekbank.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alish.geekbank.databinding.ItemDetailCardBinding
import com.alish.geekbank.presentation.base.BaseComparator1

import com.alish.geekbank.presentation.models.HistoryModelUI

class CardDetailListAdapter : ListAdapter<HistoryModelUI, CardDetailListAdapter.ViewHolder>(
    BaseComparator1()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDetailCardBinding.inflate(LayoutInflater.from(
                parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemDetailCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: HistoryModelUI) = with(binding) {
            toCard.text = item.toCard
            money.text = item.money.toString()
        }
    }
}