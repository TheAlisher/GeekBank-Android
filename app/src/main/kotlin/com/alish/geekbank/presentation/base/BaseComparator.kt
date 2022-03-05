package com.alish.geekbank.presentation.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class BaseComparator<T : IBaseDiffModel> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        return oldItem.url == newItem.url
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        return oldItem == newItem
    }
}