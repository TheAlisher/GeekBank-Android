package com.alish.geekbank.domain.usecases.firestore


import com.alish.geekbank.domain.repositories.firestore.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    operator fun invoke() =
        historyRepository.fetchHistory()
}