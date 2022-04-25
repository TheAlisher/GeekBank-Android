package com.alish.geekbank.domain.usecases.firestore

import com.alish.geekbank.domain.repositories.firestore.ProfileRepository
import javax.inject.Inject

class DownloadProfileImageUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend fun downloadProfileImage(id: String) = repository.downloadProfileImage(id)
}