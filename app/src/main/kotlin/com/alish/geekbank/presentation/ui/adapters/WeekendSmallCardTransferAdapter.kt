package com.alish.geekbank.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alish.geekbank.databinding.ItemTransferListBinding
import com.alish.geekbank.presentation.models.CardModel

class WeekendSmallCardTransferAdapter :
    RecyclerView.Adapter<WeekendSmallCardTransferAdapter.TransferViewHolder>() {

    private val model = ArrayList<CardModel>()

    fun addModel(model: ArrayList<CardModel>) {
        this.model.addAll(model)
        notifyDataSetChanged()
    }

    fun clear() {
        val size: Int = model.size
        model.clear()
        notifyItemRangeRemoved(0, size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransferViewHolder {
        return TransferViewHolder(
            ItemTransferListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TransferViewHolder, position: Int) {
        holder.onBind(model[position])
    }


    override fun getItemCount(): Int {
        return model.size
    }

    inner class TransferViewHolder(private val binding: ItemTransferListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CardModel) = with(binding) {
            cardMoney.text = item.cardMoney.toString()
            cardNumber.text = item.cardNumber
        }
    }
}