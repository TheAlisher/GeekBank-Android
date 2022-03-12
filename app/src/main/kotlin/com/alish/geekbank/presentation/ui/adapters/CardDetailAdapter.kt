package com.alish.geekbank.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alish.geekbank.databinding.ItemCardListBinding
import com.alish.geekbank.presentation.models.CardModel


class CardDetailAdapter() :
    RecyclerView.Adapter<CardDetailAdapter.ViewHolder>() {

    private val model = ArrayList<CardModel>()

    fun addModel(model: ArrayList<CardModel>){
        this.model.addAll(model)
        notifyDataSetChanged()
    }

    fun clear() {
        val size: Int = model.size
        model.clear()
        notifyItemRangeRemoved(0, size)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCardListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }

    inner class ViewHolder(private val binding: ItemCardListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: CardModel) = with(binding) {
            nameCard.text = item.name
            dataCard.text = item.date
            roomCard.text = item.cardNumber
        }
    }




}