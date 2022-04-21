package com.alish.geekbank.domain.usecases.firestore

import com.alish.geekbank.domain.repositories.firestore.EditProfileRepository
import javax.inject.Inject

class UploadEditProfileImageUseCase @Inject constructor(
    private val repository: EditProfileRepository
) {
    suspend fun uploadProfileImage(file: ByteArray?, id: String) =
        repository.uploadEditProfileImage(file, id)
}