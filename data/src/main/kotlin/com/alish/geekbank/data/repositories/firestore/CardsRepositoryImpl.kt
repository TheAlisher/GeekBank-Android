package com.alish.geekbank.data.repositories.firestore

import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.data.repositories.base.BaseRepository
import com.alish.geekbank.domain.models.firestore.CardModel
import com.alish.geekbank.domain.repositories.firestore.CardsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val preferencesHelper: PreferencesHelper
) :
    BaseRepository(), CardsRepository {

    private val userCollection = firestore.collection(Constants.COLLECTION_USERS)


    override fun fetchCards() = doRequest {
        userCollection.document(preferencesHelper.getString(Constants.USER_ID).toString())
            .collection(Constants.COLLECTION_CARDS).get().await().documents.mapNotNull { document->
            document.toObject(CardModel::class.java)
        }
    }

    override suspend fun updateCards(map: HashMap<String, Any>,cardNumber: String) {
        userCollection.document(preferencesHelper.getString(Constants.USER_ID).toString())
            .collection(Constants.COLLECTION_CARDS).document(cardNumber).update(map).await()
    }

}