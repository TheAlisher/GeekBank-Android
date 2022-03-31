package com.alish.geekbank.presentation.ui.adapters.fingerparint

import androidx.recyclerview.widget.DiffUtil
import com.alish.geekbank.presentation.models.CardModelUI

class FingerprintDiffUtil(
    private val fingerprints: List<ItemFingerprint<*, *>>,
) : DiffUtil.ItemCallback<CardModelUI>() {

    override fun areItemsTheSame(oldItem: CardModelUI, newItem: CardModelUI): Boolean {
        if (oldItem::class != newItem::class) return false

        return getItemCallback(oldItem).areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: CardModelUI, newItem: CardModelUI): Boolean {
        if (oldItem::class != newItem::class) return false

        return getItemCallback(oldItem).areContentsTheSame(oldItem, newItem)
    }

    private fun getItemCallback(
        item: CardModelUI,
    ): DiffUtil.ItemCallback<CardModelUI> = fingerprints.find { it.isRelativeItem(item) }
        ?.getDiffUtil()
        ?.let { it as DiffUtil.ItemCallback<CardModelUI> }
        ?: throw IllegalStateException("DiffUtil not found for $item")
}