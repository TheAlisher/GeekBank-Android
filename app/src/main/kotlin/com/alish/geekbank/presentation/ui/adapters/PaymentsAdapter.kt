package com.alish.geekbank.presentation.ui.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alish.geekbank.databinding.ItemNewsBinding
import com.alish.geekbank.databinding.PaymentsItemBinding
import com.alish.geekbank.presentation.base.BaseComparator
import com.alish.geekbank.presentation.extensions.setImage
import com.alish.geekbank.presentation.models.NewsModelUI
import com.alish.geekbank.presentation.models.PaymentsModel
import java.util.Collections.list

class PaymentsAdapter(val list: ArrayList<PaymentsModel>,
                      private val clickItem: () -> Unit
) :
    RecyclerView.Adapter<PaymentsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PaymentsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val binding: PaymentsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                list[absoluteAdapterPosition].let {
                    clickItem()
                }
            }
        }

        fun onBind(model: PaymentsModel) = with(binding) {
            textInternet.text = model.id
            imageInternet.setImageResource(model.image!!)
        }
    }


}