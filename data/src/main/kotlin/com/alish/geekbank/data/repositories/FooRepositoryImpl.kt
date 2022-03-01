package com.alish.geekbank.data.repositories

import com.alish.geekbank.data.remote.apiservices.FooApiService
import com.alish.geekbank.data.remote.dtos.toDomain
import com.alish.geekbank.data.remote.pagingsources.CardsPagingSource
import com.alish.geekbank.data.repositories.base.BaseRepository
import com.alish.geekbank.domain.repositories.FooRepository
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val service: FooApiService,
) : BaseRepository(), FooRepository {

    override fun fetchFoo() = doRequest {
        service.fetchCards().toDomain()
    }

    fun fetchFooPaging() = doPagingRequest(CardsPagingSource(service))
}