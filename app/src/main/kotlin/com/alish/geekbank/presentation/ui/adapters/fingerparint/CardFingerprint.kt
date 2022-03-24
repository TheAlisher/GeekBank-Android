package com.alish.geekbank.presentation.ui.adapters.fingerparint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.alish.geekbank.R
import com.alish.geekbank.databinding.ItemTransferListBinding
import com.alish.geekbank.presentation.base.BaseDiffUtilCard
import com.alish.geekbank.presentation.base.BaseViewHolder
import com.alish.geekbank.presentation.models.CardModelUI

class CardFingerprint : ItemFingerprint<ItemTransferListBinding, CardModelUI> {
    override fun isRelativeItem(item: BaseDiffUtilCard) = item is CardModelUI

    override fun getLayoutId() = R.layout.item_transfer_list

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<ItemTransferListBinding, CardModelUI> {
        val binding = ItemTransferListBinding.inflate(layoutInflater, parent, false)
        return CardViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<CardModelUI>() {
        override fun areItemsTheSame(oldItem: CardModelUI, newItem: CardModelUI) =
            oldItem.cardNumber == oldItem.cardNumber

        override fun areContentsTheSame(oldItem: CardModelUI, newItem: CardModelUI) =
            oldItem == oldItem
    }
}

class CardViewHolder(binding: ItemTransferListBinding) :
    BaseViewHolder<ItemTransferListBinding, CardModelUI>(binding) {
    override fun onBind(item: CardModelUI) = with(binding) {
        cardNumber.text = item.cardNumber
        cardMoney.text = item.money.toString()
    }
}