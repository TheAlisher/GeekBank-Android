package com.alish.geekbank.domain.usecases.firestore


import com.alish.geekbank.domain.repositories.firestore.SignInRepository
import javax.inject.Inject

class FetchUsersDataUseCase @Inject constructor(
    private  val repository: SignInRepository
) {
    operator fun invoke() = repository.fetchAccount()
}