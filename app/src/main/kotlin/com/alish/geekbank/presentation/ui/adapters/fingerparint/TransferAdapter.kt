package com.alish.geekbank.presentation.ui.adapters.fingerparint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.alish.geekbank.presentation.base.BaseViewHolder
import com.alish.geekbank.presentation.models.CardModelUI

class TransferAdapter(
    private val fingerprints: List<ItemFingerprint<*, *>>,
) : ListAdapter<CardModelUI, BaseViewHolder<ViewBinding, CardModelUI>>(FingerprintDiffUtil(
    fingerprints)) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<ViewBinding, CardModelUI> {
        val inflater = LayoutInflater.from(parent.context)
        return fingerprints.find { it.getLayoutId() == viewType }
            ?.getViewHolder(inflater, parent)
            ?.let { it as BaseViewHolder<ViewBinding, CardModelUI> }
            ?: throw IllegalArgumentException("View type not found: $viewType")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, CardModelUI>, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        return fingerprints.find { it.isRelativeItem(item) }
            ?.getLayoutId()
            ?: throw IllegalArgumentException("View type not found: $item")
    }
}