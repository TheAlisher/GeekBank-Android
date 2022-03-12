package com.alish.geekbank.presentation.base

import androidx.recyclerview.widget.DiffUtil

interface IBaseDiffModelNews {
    val url: String?
    override fun equals(other: Any?): Boolean
}

class BaseDiffUtilItemNewsCallback<Int : IBaseDiffModelNews> : DiffUtil.ItemCallback<Int>() {

    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }
}