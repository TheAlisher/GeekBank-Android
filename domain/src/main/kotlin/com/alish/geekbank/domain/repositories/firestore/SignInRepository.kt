package com.alish.geekbank.domain.repositories.firestore

import com.alish.geekbank.common.resource.Resource
import com.alish.geekbank.domain.models.firestore.UsersModel
import kotlinx.coroutines.flow.Flow
interface SignInRepository {

    fun fetchAccount(): Flow<Resource<List<UsersModel>>>
}