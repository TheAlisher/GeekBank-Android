package com.alish.geekbank.domain.usecases.firestore


import com.alish.geekbank.domain.repositories.firestore.CardsRepository
import javax.inject.Inject

class UpdateCardsUseCase @Inject constructor(
    private val repository: CardsRepository
) {
    suspend fun updateAccount(map: HashMap<String, Any>, cardNumber: String) {
        repository.updateCards(map, cardNumber)
    }
}