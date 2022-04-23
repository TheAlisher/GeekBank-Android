package com.alish.geekbank.domain.usecases.firestore

import com.alish.geekbank.domain.repositories.firestore.ProfileRepository
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(
    private val repository: ProfileRepository,
) {
    suspend fun uploadProfileImage(file: ByteArray?, id: String) =
        repository.uploadProfileImage(file, id)
}