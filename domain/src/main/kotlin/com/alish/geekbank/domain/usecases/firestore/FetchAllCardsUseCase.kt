package com.alish.geekbank.domain.usecases.firestore

import com.alish.geekbank.domain.repositories.firestore.CardsRepository
import javax.inject.Inject

class FetchAllCardsUseCase @Inject constructor(
    private val cardsRepository: CardsRepository
) {
    operator fun invoke() = cardsRepository.fetchAllCards()
}