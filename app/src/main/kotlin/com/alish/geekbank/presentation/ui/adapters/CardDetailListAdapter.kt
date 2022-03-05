package com.alish.geekbank.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alish.geekbank.databinding.ItemDetailCardBinding
import com.alish.geekbank.presentation.base.BaseDiffUtilItemCallback
import com.alish.geekbank.presentation.models.CardListUIModel
import com.alish.geekbank.presentation.models.CardsUIModel

class CardDetailListAdapter : ListAdapter<CardListUIModel, CardDetailListAdapter.ViewHolder>(
    BaseDiffUtilItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDetailCardBinding.inflate(LayoutInflater.from(
                parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))

    }

    inner class ViewHolder(private val binding: ItemDetailCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CardListUIModel) = with(binding) {
            logoAirbnb.load(item.image)
            txtAirbnb.text = item.name
        }
    }
}