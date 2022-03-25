package com.alish.geekbank.presentation.ui.fragments.home.allnews.detailnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentDetailNewsBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.ui.fragments.home.HomeViewModel

class DetailNews : BaseFragment<HomeViewModel,FragmentDetailNewsBinding>(R.layout.fragment_detail_news) {

    override val viewModel: HomeViewModel by viewModels()
    override val binding: FragmentDetailNewsBinding by viewBinding(FragmentDetailNewsBinding::bind)

    override fun initialize() {
       // val model = DetailNewsArgs.fromBundle(requireArguments()).model
//        binding.imageNews.load(model.urlToImage)
//        binding.textDescription.text = model.description
//        binding.textTitle.text = model.title
    }

}