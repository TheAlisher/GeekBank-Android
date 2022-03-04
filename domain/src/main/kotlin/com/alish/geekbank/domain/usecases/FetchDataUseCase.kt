package com.alish.geekbank.domain.usecases

import com.alish.geekbank.domain.repositories.SignInRepository
import javax.inject.Inject

class FetchDataUseCase @Inject constructor(
    private  val repository: SignInRepository
) {
    operator fun invoke() = repository.fetchData()
}