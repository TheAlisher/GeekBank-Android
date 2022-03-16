package com.alish.geekbank.domain.repositories

import com.alish.geekbank.common.resource.Resource
import com.alish.geekbank.domain.models.ExchangeModel
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {
    fun fetchExchange(): Flow<Resource<ExchangeModel>>
}