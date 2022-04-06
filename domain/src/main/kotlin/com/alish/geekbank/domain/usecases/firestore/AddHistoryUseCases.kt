package com.alish.geekbank.domain.usecases.firestore


import com.alish.geekbank.domain.repositories.firestore.HistoryRepository
import javax.inject.Inject

class AddHistoryUseCases @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend fun addHistory(map: HashMap<String,Any>) {
        repository.addHistory(map)
    }
}