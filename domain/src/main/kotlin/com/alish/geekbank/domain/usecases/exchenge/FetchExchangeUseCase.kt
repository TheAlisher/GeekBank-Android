package com.alish.geekbank.domain.usecases.exchenge

import com.alish.geekbank.domain.repositories.ExchangeRepository
import javax.inject.Inject

class FetchExchangeUseCase @Inject constructor(
    private val repository: ExchangeRepository
) {
     operator fun invoke() = repository.fetchExchange()
}