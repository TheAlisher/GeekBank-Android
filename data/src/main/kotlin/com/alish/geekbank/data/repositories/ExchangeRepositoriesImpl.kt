package com.alish.geekbank.data.repositories

import com.alish.geekbank.data.remote.apiservices.ExchangeApiService
import com.alish.geekbank.data.remote.dtos.toDomain
import com.alish.geekbank.data.repositories.base.BaseRepository
import com.alish.geekbank.domain.repositories.ExchangeRepository
import javax.inject.Inject

class ExchangeRepositoriesImpl @Inject constructor(
    private val service: ExchangeApiService,
) : BaseRepository(), ExchangeRepository {
    override fun fetchExchange() = doRequest {
        service.fetchExchange().toDomain()
    }
}