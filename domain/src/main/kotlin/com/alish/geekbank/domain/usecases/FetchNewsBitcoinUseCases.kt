package com.alish.geekbank.domain.usecases

import com.alish.geekbank.domain.repositories.NewsRepository
import javax.inject.Inject

class FetchNewsBitcoinUseCases @Inject constructor(
    private val repository: NewsRepository
) {

    operator fun invoke() = repository.fetchNewsBitcoin()
}