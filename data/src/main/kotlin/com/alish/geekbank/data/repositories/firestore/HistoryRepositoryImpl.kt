package com.alish.geekbank.data.repositories.firestore

import com.alish.geekbank.common.constants.Constants
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import com.alish.geekbank.data.repositories.base.BaseRepository
import com.alish.geekbank.domain.models.firestore.HistoryModel
import com.alish.geekbank.domain.repositories.firestore.HistoryRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val preferencesHelper: PreferencesHelper
) : BaseRepository(), HistoryRepository {

    private val userCollection = firestore.collection(Constants.COLLECTION_USERS)

    override suspend fun addHistory(map: HashMap<String, Any>) {
        userCollection.document(preferencesHelper.getString(Constants.USER_ID).toString())
            .collection(Constants.COLLECTION_HISTORY)
            .add(map).await()
    }

    override fun fetchHistory() =doRequest{
        userCollection.document(preferencesHelper.getString(Constants.USER_ID).toString())
            .collection(Constants.COLLECTION_HISTORY).get().await().toObjects(HistoryModel::class.java)

    }
}