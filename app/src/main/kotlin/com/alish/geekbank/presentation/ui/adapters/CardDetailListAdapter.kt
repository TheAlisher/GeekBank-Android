package com.alish.geekbank.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alish.geekbank.R
import com.alish.geekbank.databinding.ItemDateTransferBinding
import com.alish.geekbank.databinding.ItemDetailCardBinding
import com.alish.geekbank.databinding.ItemPaymentHistoryBinding
import com.alish.geekbank.databinding.ItemTransferPlusBinding
import com.alish.geekbank.presentation.base.BaseComparator1

import com.alish.geekbank.presentation.models.HistoryModelUI
import java.lang.IllegalStateException

class CardDetailListAdapter : ListAdapter<HistoryModelUI, RecyclerView.ViewHolder>(
    BaseComparator1()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            R.layout.item_transfer_plus -> ViewHolderPlus(ItemTransferPlusBinding.inflate(
                LayoutInflater.from(parent.context),parent,false))
            R.layout.item_detail_card -> ViewHolderMinus(ItemDetailCardBinding.inflate(
                LayoutInflater.from(parent.context),parent,false))
            R.layout.item_payment_history -> ViewHolderService(ItemPaymentHistoryBinding.inflate(
                LayoutInflater.from(parent.context),parent,false))
            else -> throw IllegalArgumentException("View type not found: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)){
            R.layout.item_detail_card ->getItem(position)?.let {
                (holder as ViewHolderMinus).onBind(it)
            }
            R.layout.item_transfer_plus ->getItem(position)?.let {
                (holder as ViewHolderPlus).onBind(it)
            }
            R.layout.item_payment_history ->getItem(position)?.let {
                (holder as ViewHolderService).onBind(it)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when (getItem(position).condition) {
            "plus" -> {
                R.layout.item_transfer_plus
            }
            "minus" -> {
                R.layout.item_detail_card
            }
            "service" ->{
                R.layout.item_payment_history
            }
            else -> {
                R.layout.item_date_transfer
            }
        }
    }

    inner class ViewHolderMinus(private val binding: ItemDetailCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: HistoryModelUI) = with(binding) {
            val date1 = item.dateTime?.substring(11,16)
            toCard.text = item.toCard
            money.text = item.money.toString()
            time.text = date1
        }
    }

    inner class ViewHolderPlus(private val binding: ItemTransferPlusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: HistoryModelUI) = with(binding) {
            val date1 = item.dateTime?.substring(11,16)
            fromCard.text = item.fromCard
            money.text = item.money.toString()
            time.text = date1
        }
    }
    inner class ViewHolderService(private val binding: ItemPaymentHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: HistoryModelUI) = with(binding) {
            val date1 = item.dateTime?.substring(11,16)
            toPay.text = item.toCard
            money.text = item.money.toString()
            time.text = date1
        }
    }
    inner class ViewHolderDate(private val binding: ItemDateTransferBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: HistoryModelUI) = with(binding) {
            val date1 = item.dateTime?.substring(1,10)
            date.text = date1
        }
    }
}




