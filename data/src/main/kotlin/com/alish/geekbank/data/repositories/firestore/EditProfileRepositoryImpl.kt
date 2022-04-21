package com.alish.geekbank.data.repositories.firestore

import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.data.repositories.base.BaseRepository
import com.alish.geekbank.domain.repositories.firestore.EditProfileRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EditProfileRepositoryImpl @Inject constructor(
    private val storageReference: StorageReference,
    private val firestore: FirebaseFirestore,
    private val preferencesHelper: PreferencesHelper,
) : EditProfileRepository, BaseRepository() {


    override suspend fun uploadEditProfileImage(file: ByteArray?, id: String) {
        file?.let {
            storageReference.child("profileimages/$id")
                .putBytes(it)
                .await()
        }
    }

    override suspend fun downloadEditProfileImage(id: String): String? {
        return storageReference.child("profileimages/$id")
            .downloadUrl.await().toString()
    }
}