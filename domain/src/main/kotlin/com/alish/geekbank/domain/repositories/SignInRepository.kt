package com.alish.geekbank.domain.repositories

import com.alish.geekbank.common.resource.Resource
import com.alish.geekbank.domain.models.UsersModel
import kotlinx.coroutines.flow.Flow

interface SignInRepository {
    fun fetchData(): Flow<Resource<List<UsersModel?>>>
}