package com.alish.geekbank.domain.repositories.firestore

import com.alish.geekbank.common.resource.Resource
import com.alish.geekbank.domain.models.firestore.CardModel
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun fetchCards():Flow<Resource<List<CardModel?>>>

    suspend fun updateCards(map: HashMap<String,Any>,cardNumber: String)

    suspend fun createCard(map: HashMap<String,Any>,userId: String,cardNum: String)

    fun fetchAllCards():Flow<Resource<List<CardModel?>>>

    suspend fun addCard(map: HashMap<String,Any>,cardNum: String)


}