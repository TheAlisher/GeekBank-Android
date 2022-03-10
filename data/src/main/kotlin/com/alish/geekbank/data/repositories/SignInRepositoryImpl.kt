package com.alish.geekbank.data.repositories

import com.alish.geekbank.data.repositories.base.BaseRepository
import com.alish.geekbank.domain.models.UsersModel
import com.alish.geekbank.domain.repositories.SignInRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) :
    BaseRepository(), SignInRepository {
    private val userCollection = firestore.collection("users")
    override fun fetchData() = doRequest {
        userCollection.get().await().documents.mapNotNull { document ->
            document.toObject(UsersModel::class.java)
        }


    }
}