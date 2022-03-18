package com.alish.geekbank.domain.usecases

import com.alish.geekbank.domain.repositories.FirestoreRepository
import javax.inject.Inject

class FetchCardDataUseCase @Inject constructor(
    private val repository: FirestoreRepository) {

    operator fun invoke()=repository.fetchCards()
}