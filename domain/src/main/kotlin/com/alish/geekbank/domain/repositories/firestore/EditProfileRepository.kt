package com.alish.geekbank.domain.repositories.firestore

interface EditProfileRepository {
    suspend fun uploadEditProfileImage(file: ByteArray?, id: String, navigate: () -> Unit)

    suspend fun downloadEditProfileImage(id: String): String?
}