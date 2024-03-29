package com.alish.geekbank.domain.repositories.firestore

import com.alish.geekbank.common.resource.Resource
import com.alish.geekbank.domain.models.firestore.UsersModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun fetchAccount(): Flow<Resource<UsersModel?>>

    suspend fun accountChanges(
        hashMap: HashMap<String, Any>,
    )

    suspend fun createAccount(
        hashMap: HashMap<String, Any>,
        id: String,
    )

    suspend fun uploadProfileImage(file: ByteArray?, id: String)

    suspend fun downloadProfileImage(id: String): String?
}