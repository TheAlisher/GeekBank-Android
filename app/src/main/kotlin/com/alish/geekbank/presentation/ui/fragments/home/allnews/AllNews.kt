package com.alish.geekbank.presentation.ui.fragments.home.allnews

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alish.geekbank.R
import com.alish.geekbank.databinding.FragmentAllNewsBinding
import com.alish.geekbank.presentation.base.BaseFragment
import com.alish.geekbank.presentation.models.NewsModelUI
import com.alish.geekbank.presentation.state.UIState
import com.alish.geekbank.presentation.ui.adapters.NewsAdapter
import com.alish.geekbank.presentation.ui.fragments.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllNews() : BaseFragment<HomeViewModel, FragmentAllNewsBinding>(R.layout.fragment_all_news) {

    override val viewModel: HomeViewModel by viewModels()
    override val binding: FragmentAllNewsBinding by viewBinding(FragmentAllNewsBinding::bind)
    private val adapter: NewsAdapter = NewsAdapter(this::clickItemNews)

    private fun clickItemNews(model: NewsModelUI) {
        findNavController().navigate(AllNewsDirections.actionAllNewsToDetailNews(model))
    }

    override fun initialize() = with(binding) {
        rvNews.adapter = adapter
    }

    override fun setupRequests() {
        viewModel.newsState.collectUIState {
            when (it) {
                is UIState.Error -> {
                }
                is UIState.Loading -> {
                }
                is UIState.Success -> {
                    var list: ArrayList<NewsModelUI> = ArrayList()
                    list.addAll(it.data)
                    list.shuffle()
                    adapter.submitList(list)
                }
            }
        }
    }
}