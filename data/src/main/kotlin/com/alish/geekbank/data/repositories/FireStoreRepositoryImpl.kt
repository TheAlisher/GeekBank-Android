package com.alish.geekbank.data.repositories

import com.alish.geekbank.common.resource.Resource
import com.alish.geekbank.data.repositories.base.BaseRepository
import com.alish.geekbank.domain.models.CardModel
import com.alish.geekbank.domain.models.UsersModel
import com.alish.geekbank.domain.repositories.FirestoreRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireStoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) :
    BaseRepository(), FirestoreRepository {
    private val userCollection = firestore.collection("users")
    private val cardCollection = firestore.collection("cards")
    override fun fetchData() = doRequest {
        userCollection.get().await().documents.mapNotNull { document ->
            document.toObject(UsersModel::class.java)
        }
    }

    override suspend fun accountChanges(
        id: String,
        hashMap: HashMap<String,Any>
    ) {
       cardCollection.document(id).update(hashMap).await()


    }

    override fun fetchCards() = doRequest {
        cardCollection.get().await().documents.mapNotNull { document->
            document.toObject(CardModel::class.java)
        }
    }
}