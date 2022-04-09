package com.alish.geekbank.domain.usecases.firestore


import com.alish.geekbank.domain.repositories.firestore.ProfileRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun updateAccount(map: HashMap<String,Any>) {
        repository.accountChanges(map)}
}