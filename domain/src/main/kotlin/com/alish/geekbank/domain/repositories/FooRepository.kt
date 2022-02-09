package com.alish.geekbank.domain.repositories

import com.alish.geekbank.common.resource.Resource
import com.alish.geekbank.domain.models.Foo
import kotlinx.coroutines.flow.Flow

interface FooRepository {

    fun fetchFoo(): Flow<Resource<Foo>>
}