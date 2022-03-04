package com.alish.geekbank.presentation.ui.adapters


import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alish.geekbank.databinding.ItemNewsBinding
import com.alish.geekbank.presentation.base.BaseComparator
import com.alish.geekbank.presentation.models.NewsModelUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener


class NewsAdapter(private val clickNewsItem: (model: NewsModelUI) -> Unit): ListAdapter<NewsModelUI,NewsAdapter.NewsViewHolder>(BaseComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }




    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {


        init {
            itemView.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let {
                    clickNewsItem(it)
                }
            }

        }
        fun onBind(model: NewsModelUI) = with(binding) {
            textTitle.text = model.title
            textDescription.text = model.description
            dateTop.text = model.publishedAt
            newsImage.load(model.urlToImage)
        }
    }


}
