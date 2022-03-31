package com.alish.geekbank.domain.repositories

import com.alish.geekbank.common.resource.Resource
import com.alish.geekbank.domain.models.CardModel
import com.alish.geekbank.domain.models.UsersModel
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    fun fetchData(
    ): Flow<Resource<List<UsersModel?>>>

    suspend fun accountChanges(
        id: String,
        hashMap: HashMap<String,Any>
    )
    fun fetchCards():Flow<Resource<List<CardModel?>>>

}