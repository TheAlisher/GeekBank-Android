package com.alish.geekbank.domain.repositories.firestore

import com.alish.geekbank.common.resource.Resource
import com.alish.geekbank.domain.models.firestore.CardModel
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun fetchCards():Flow<Resource<List<CardModel?>>>

    suspend fun updateCards(map: HashMap<String,Any>,cardNumber: String)

}