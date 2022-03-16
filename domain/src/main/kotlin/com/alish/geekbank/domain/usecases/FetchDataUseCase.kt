package com.alish.geekbank.domain.usecases

import com.alish.geekbank.domain.repositories.FirestoreRepository
import javax.inject.Inject

class FetchDataUseCase @Inject constructor(
    private  val repository: FirestoreRepository
) {
    operator fun invoke() = repository.fetchData()
}