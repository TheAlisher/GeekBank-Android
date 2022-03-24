package com.alish.geekbank.presentation.ui.adapters.fingerparint

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.alish.geekbank.R
import com.alish.geekbank.databinding.ItemListTransferNumberBinding
import com.alish.geekbank.presentation.base.BaseDiffUtilCard
import com.alish.geekbank.presentation.base.BaseViewHolder
import com.alish.geekbank.presentation.models.CardModelUI

class EnterCardNumberFingerPrint : ItemFingerprint<ItemListTransferNumberBinding, CardModelUI> {
    override fun isRelativeItem(item: BaseDiffUtilCard) = item is CardModelUI

    override fun getLayoutId() = R.layout.item_list_transfer_number

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<ItemListTransferNumberBinding, CardModelUI> {
        val binding = ItemListTransferNumberBinding.inflate(layoutInflater, parent, false)
        return EnterCardNumberViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<CardModelUI>() {
        override fun areItemsTheSame(oldItem: CardModelUI, newItem: CardModelUI) =
            oldItem.cardNumber == oldItem.cardNumber

        override fun areContentsTheSame(oldItem: CardModelUI, newItem: CardModelUI) =
            oldItem == oldItem
    }
}

class EnterCardNumberViewHolder(binding: ItemListTransferNumberBinding) :
    BaseViewHolder<ItemListTransferNumberBinding, CardModelUI>(binding) {
    override fun onBind(item: CardModelUI): Unit = with(binding) {
        val cardNumber = enterCardNumber.text.toString().trim()
        qrBtn.setOnClickListener {

        }
    }
}