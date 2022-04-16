package com.alish.geekbank.domain.usecases.firestore

import com.alish.geekbank.domain.repositories.firestore.CardsRepository
import javax.inject.Inject

class AddCardUseCase @Inject constructor(
    private val cardsRepository: CardsRepository
) {
    suspend fun addCards(map: HashMap<String,Any>,cardNum: String){
        cardsRepository.addCard(map,cardNum)
    }
}