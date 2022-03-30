package com.alish.geekbank.domain.usecases.firestore


import com.alish.geekbank.domain.repositories.firestore.ProfileRepository
import javax.inject.Inject

class FetchUserDataUseCase @Inject constructor(
    private  val repository: ProfileRepository
) {
    operator fun invoke() = repository.fetchAccount()
}