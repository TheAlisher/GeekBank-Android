package com.alish.geekbank.presentation.ui.fragments.freezeCard

import com.alish.geekbank.domain.usecases.firestore.UpdateCardsUseCase
import com.alish.geekbank.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FreezeCardViewModel @Inject constructor(
    private val updateCardsUseCase: UpdateCardsUseCase
): BaseViewModel() {

    suspend fun blockCard(cardNum: String){
        val map = HashMap<String,Any>()
        map["blocked"] = true
        updateCardsUseCase.updateAccount(map,cardNum)
    }


}