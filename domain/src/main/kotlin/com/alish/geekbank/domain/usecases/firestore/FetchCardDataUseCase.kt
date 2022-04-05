package com.alish.geekbank.domain.usecases.firestore

import com.alish.geekbank.domain.repositories.firestore.CardsRepository
import javax.inject.Inject

class FetchCardDataUseCase @Inject constructor(
    private val repository: CardsRepository
) {

    operator fun invoke()=repository.fetchCards()
}