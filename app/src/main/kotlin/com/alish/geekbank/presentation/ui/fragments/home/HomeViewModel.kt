package com.alish.geekbank.presentation.ui.fragments.home

import com.alish.geekbank.domain.usecases.*
import com.alish.geekbank.domain.usecases.firestore.FetchCardDataUseCase
import com.alish.geekbank.presentation.base.BaseViewModel
import com.alish.geekbank.presentation.models.*
import com.alish.geekbank.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(private val fetchNewsUseCase: FetchNewsUseCases
,private val fetchNewsBankUseCase: FetchNewsBankUseCase,
private val fetchNewsBitcoinUseCases: FetchNewsBitcoinUseCases,
private val fetchCardDataUseCase: FetchCardDataUseCase) : BaseViewModel() {

    private val _newsState =
        MutableStateFlow<UIState<List<NewsModelUI>>>(UIState.Loading())
    val newsState: StateFlow<UIState<List<NewsModelUI>>> = _newsState

    private val _stateCard =
        MutableStateFlow<UIState<List<CardModelUI?>>>(UIState.Loading())
    val stateCard: StateFlow<UIState<List<CardModelUI?>>> = _stateCard.asStateFlow()

    init {
        fetchNews()
        fetchNewsBank()
        fetchNewsBitcoin()
        fetchUser()
    }

    private fun fetchUser(){
        fetchCardDataUseCase().collectRequest(_stateCard){it.map { data-> data?.toUI() }}
    }

    private fun fetchNews(){
        fetchNewsUseCase().collectRequest(_newsState){
            it.map { data -> data.toUI() }}

    }
    private fun fetchNewsBank(){
        fetchNewsBankUseCase().collectRequest(_newsState){it.map { data -> data.toUI() }}

    }
    private fun fetchNewsBitcoin(){
        fetchNewsBitcoinUseCases().collectRequest(_newsState){it.map { data -> data.toUI() }}

    }
}