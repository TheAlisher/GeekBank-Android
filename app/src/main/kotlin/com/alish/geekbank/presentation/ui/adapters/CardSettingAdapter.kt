package com.alish.geekbank.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alish.geekbank.databinding.ItemCardSettingBinding
import com.alish.geekbank.presentation.base.BaseComparatorCard
import com.alish.geekbank.presentation.models.CardModelUI


class CardSettingAdapter : ListAdapter<CardModelUI, CardSettingAdapter.CardSettingViewHolder>(
    BaseComparatorCard()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardSettingViewHolder {
        return CardSettingViewHolder(
            ItemCardSettingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardSettingViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class CardSettingViewHolder(private val binding: ItemCardSettingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: CardModelUI) = with(binding) {
            nameCard.text = item.fullName
            numberCard.text = item.cardNumber
            money.text = item.money.toString()
            dateCard.text = item.date
        }

    }
}