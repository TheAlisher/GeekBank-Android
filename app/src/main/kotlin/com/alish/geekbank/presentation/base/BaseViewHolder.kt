package com.alish.geekbank.presentation.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<out V : ViewBinding, in I : BaseDiffUtilCard>(
    val binding: V
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun onBind(item: I)
}