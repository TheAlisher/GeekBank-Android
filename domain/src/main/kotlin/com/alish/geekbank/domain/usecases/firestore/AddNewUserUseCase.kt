package com.alish.geekbank.domain.usecases.firestore

import com.alish.geekbank.domain.repositories.firestore.ProfileRepository
import javax.inject.Inject

class AddNewUserUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun updateAccount(map: HashMap<String,Any>,id: String) {
        profileRepository.createAccount(map,id)}
}