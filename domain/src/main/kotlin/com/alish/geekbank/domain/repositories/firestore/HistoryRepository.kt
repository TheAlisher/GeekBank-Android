package com.alish.geekbank.domain.repositories.firestore

import com.alish.geekbank.common.resource.Resource

import com.alish.geekbank.domain.models.firestore.HistoryModel
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
   suspend fun addHistory(
       map: HashMap<String,Any>
   )
   fun fetchHistory(): Flow<Resource<List<HistoryModel>>>
}