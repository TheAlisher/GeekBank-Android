package com.alish.geekbank.data.repositories.firestore

import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.repositories.base.BaseRepository
import com.alish.geekbank.domain.models.firestore.UsersModel
import com.alish.geekbank.domain.repositories.firestore.SignInRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : BaseRepository(),
    SignInRepository {

    private val usersCollection = firestore.collection(Constants.COLLECTION_USERS)

    override fun fetchAccount() = doRequest {
        usersCollection.get().await().documents.mapNotNull { document ->
            document.toObject(UsersModel::class.java)
        }
    }


}