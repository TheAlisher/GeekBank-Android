package com.alish.geekbank.domain.usecases.foo

import com.alish.geekbank.domain.repositories.NewsRepository
import javax.inject.Inject

class FetchFooUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke() = repository.fetchNews()
}