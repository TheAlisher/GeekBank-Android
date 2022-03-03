package com.alish.geekbank.presentation.ui.fragments.home

import com.alish.geekbank.domain.usecases.FetchNewsUseCases
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.models.NewsModelUI
import com.alish.geekbank.presentation.models.toUI
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(private val fetchNewsUseCase: FetchNewsUseCases) : BaseViewModel() {

    private val _newsState =
        MutableStateFlow<UIState<List<NewsModelUI>>>(UIState.Loading())
    val newsState: StateFlow<UIState<List<NewsModelUI>>> = _newsState
    init {
        fetchNews()
    }

    fun fetchNews(){
        fetchNewsUseCase().collectRequest(_newsState){it.map { data -> data.toUI() }}

    }
}