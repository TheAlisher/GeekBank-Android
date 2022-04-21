package com.alish.geekbank.domain.usecases.firestore

import com.alish.geekbank.domain.repositories.firestore.EditProfileRepository
import javax.inject.Inject

class DownloadEditProfileImageUseCase @Inject constructor(
    private val repository: EditProfileRepository
) {
    suspend fun downloadEditProfileImage(id: String) = repository.downloadEditProfileImage(id)
}