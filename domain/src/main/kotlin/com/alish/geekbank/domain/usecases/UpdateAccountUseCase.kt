package com.alish.geekbank.domain.usecases

import com.alish.geekbank.domain.repositories.FirestoreRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val repository: FirestoreRepository
) {
    suspend fun updateAccount(id: String, map: HashMap<String,Any>) {
        repository.accountChanges(id,map)}
}