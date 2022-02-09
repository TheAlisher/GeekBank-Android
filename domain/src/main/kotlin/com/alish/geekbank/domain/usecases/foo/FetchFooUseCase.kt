package com.alish.geekbank.domain.usecases.foo

import com.alish.geekbank.domain.repositories.FooRepository
import javax.inject.Inject

class FetchFooUseCase @Inject constructor(
    private val repository: FooRepository
) {
    operator fun invoke() = repository.fetchFoo()
}