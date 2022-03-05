package com.alish.geekbank.presentation.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class BaseComparator1 <T : BaseDiffUtil1> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        return oldItem.id== newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        return oldItem == newItem
    }
}