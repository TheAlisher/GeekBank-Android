package com.alish.geekbank.presentation.base

import androidx.recyclerview.widget.DiffUtil

interface IBaseDiffModel {
    val id: Long
    override fun equals(other: Any?): Boolean
}

class BaseDiffUtilItemCallback<Int : IBaseDiffModel> : DiffUtil.ItemCallback<Int>() {

    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }
}