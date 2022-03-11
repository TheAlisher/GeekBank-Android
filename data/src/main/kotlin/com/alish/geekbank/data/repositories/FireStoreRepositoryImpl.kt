package com.alish.geekbank.data.repositories

import com.alish.geekbank.data.repositories.base.BaseRepository
import com.alish.geekbank.domain.models.UsersModel
import com.alish.geekbank.domain.repositories.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireStoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) :
    BaseRepository(), FirestoreRepository {
    private val userCollection = firestore.collection("users")
    override fun fetchData() = doRequest {
        userCollection.get().await().documents.mapNotNull { document ->
            document.toObject(UsersModel::class.java)
        }
    }
}