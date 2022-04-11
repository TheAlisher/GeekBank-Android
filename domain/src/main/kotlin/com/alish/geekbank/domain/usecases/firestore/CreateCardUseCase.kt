package com.alish.geekbank.domain.usecases.firestore

import com.alish.geekbank.domain.repositories.firestore.CardsRepository
import javax.inject.Inject

class CreateCardUseCase @Inject constructor(private val repository: CardsRepository) {

    suspend fun createCard(map: HashMap<String,Any>,userId: String,cardNum: String){
        repository.createCard(map,userId,cardNum)

    }
}