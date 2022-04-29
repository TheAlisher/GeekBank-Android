package com.alish.geekbank.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alish.geekbank.R
import com.alish.geekbank.databinding.ItemListTransferNumberBinding
import com.alish.geekbank.databinding.ItemTransferListBinding
import com.alish.geekbank.presentation.base.BaseComparatorCard
import com.alish.geekbank.presentation.models.CardModelUI


class CardTransferAdapterTo :
    ListAdapter<CardModelUI, RecyclerView.ViewHolder>(BaseComparatorCard()) {

    private var num = 1
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
      return when(viewType){
           R.layout.item_transfer_list -> CardTransferToViewHolder(ItemTransferListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
           R.layout.item_list_transfer_number -> EditTransferViewHolder(
               ItemListTransferNumberBinding.inflate(LayoutInflater.from(parent.context),parent,false))
           else -> throw IllegalArgumentException("View type not found: $viewType")
       }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (getItemViewType(position)) {
            R.layout.item_list_transfer_number ->
                getItem(position).let {
                    (holder as CardTransferToViewHolder).onBind(it)
                }
        }

    }


    inner class CardTransferToViewHolder(
        private val binding: ItemTransferListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CardModelUI) {
            binding.cardNumber.text = item.cardNumber
            binding.cardMoney.text = item.money.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(num == 1){
            R.layout.item_list_transfer_number
        }else{
            R.layout.item_transfer_list
        }
    }

    inner class EditTransferViewHolder(
        private val binding: ItemListTransferNumberBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(s:String){
            binding.enterCardNumber.setText(s)
        }

    }

}