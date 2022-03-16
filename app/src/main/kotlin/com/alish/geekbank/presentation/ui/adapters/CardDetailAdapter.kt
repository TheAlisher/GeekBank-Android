package com.alish.geekbank.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alish.geekbank.databinding.ItemCardListBinding
import com.alish.geekbank.presentation.base.BaseComparatorCard
import com.alish.geekbank.presentation.models.CardModel

class CardDetailAdapter :
    ListAdapter<CardModel, CardDetailAdapter.CardViewHolder>(BaseComparatorCard()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            ItemCardListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class CardViewHolder(private val binding: ItemCardListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: CardModel) = with(binding) {
            nameCard.text = item.name
            dataCard.text = item.date
            roomCard.text = item.cardNumber
        }
    }
}