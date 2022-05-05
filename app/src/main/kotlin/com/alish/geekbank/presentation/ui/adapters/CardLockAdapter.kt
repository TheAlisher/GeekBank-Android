package com.alish.geekbank.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alish.geekbank.databinding.ItemCardLockListBinding
import com.alish.geekbank.presentation.base.BaseComparatorCard
import com.alish.geekbank.presentation.models.CardModelUI


class CardLockAdapter(
    private val onItemClick: (pos: String) -> Unit
) : ListAdapter<CardModelUI, CardLockAdapter.CardLockViewHolder>(BaseComparatorCard()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardLockViewHolder {
        return CardLockViewHolder(
            ItemCardLockListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardLockViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class CardLockViewHolder(private val binding: ItemCardLockListBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind(item: CardModelUI) = with(binding) {
            roomCard.text = item.cardNumber
            dateCard.text = item.date
            cash.text = item.money.toString()

            lineLock.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let {
                    it.cardNumber?.let { it1 -> onItemClick(it1) }
                }
            }
        }
    }
}