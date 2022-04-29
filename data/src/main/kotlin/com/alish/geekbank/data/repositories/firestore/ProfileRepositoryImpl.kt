package com.alish.geekbank.data.repositories.firestore

import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.data.repositories.base.BaseRepository
import com.alish.geekbank.domain.models.firestore.UsersModel
import com.alish.geekbank.domain.repositories.firestore.ProfileRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val preferencesHelper: PreferencesHelper,
    private val storage: FirebaseStorage,
    private val storageReference: StorageReference,
) : ProfileRepository, BaseRepository() {

    private val userCollection = firestore.collection(Constants.COLLECTION_USERS)


    override fun fetchAccount() = doRequest {
        userCollection.document(preferencesHelper.getString(Constants.USER_ID).toString()).get()
            .await().toObject(
                UsersModel::class.java)
    }

    override suspend fun accountChanges(hashMap: HashMap<String, Any>) {
        userCollection.document(preferencesHelper.getString(Constants.USER_ID).toString()).update(hashMap).await()
    }

    override suspend fun createAccount(hashMap: HashMap<String, Any>,id: String) {
        userCollection.document(id).set(hashMap).await()
    }

    override suspend fun uploadProfileImage(file: ByteArray?, id: String) {
        file?.let {
            storageReference.child("profileimages/$id")
                .putBytes(it)
                .await()
        }
    }

    override suspend fun downloadProfileImage(id: String): String? {
        return try {
            storageReference.child("profileimages/$id")
                .downloadUrl.await()?.toString()
        } catch (e: Exception) {
            null
        }
    }
}