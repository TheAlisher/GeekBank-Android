package com.alish.geekbank.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alish.geekbank.databinding.FragmentCardDetailBinding
import com.alish.geekbank.databinding.ItemCardListBinding
import com.alish.geekbank.databinding.ItemDetailCardBinding
import com.alish.geekbank.presentation.base.BaseDiffUtilItemCallback
import com.alish.geekbank.presentation.models.CardsUI


class CardDetailAdapter: RecyclerView.Adapter<CardDetailAdapter.ViewHolder>() {

    private val list: ArrayList<Int> = ArrayList()

    fun addImage(image: ArrayList<Int>){
        list.addAll(image)
        notifyDataSetChanged()
    }


inner class ViewHolder(private val binding: ItemCardListBinding): RecyclerView.ViewHolder(binding.root) {

    fun onBind(image: Int) = with(binding) {
        imageVisa1.load(image)
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemCardListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }



}