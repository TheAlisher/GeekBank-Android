package com.alish.geekbank.di

import com.alish.geekbank.data.repositories.ExchangeRepositoriesImpl
import com.alish.geekbank.data.repositories.FireStoreRepositoryImpl
import com.alish.geekbank.data.repositories.NewsRepositoryImpl
import com.alish.geekbank.data.repositories.firestore.*
import com.alish.geekbank.domain.repositories.ExchangeRepository
import com.alish.geekbank.domain.repositories.FirestoreRepository
import com.alish.geekbank.domain.repositories.NewsRepository
import com.alish.geekbank.domain.repositories.firestore.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindNewsRepository(repositoryImpl: NewsRepositoryImpl): NewsRepository

    @Binds
    abstract fun bindExchangeRepository(repositoryImpl: ExchangeRepositoriesImpl) : ExchangeRepository

    @Binds
    abstract fun bindCardsRepository(repositoryImpl: CardsRepositoryImpl) : CardsRepository

    @Binds
    abstract fun bindProfileRepository(repositoryProfile: ProfileRepositoryImpl) : ProfileRepository

    @Binds
    abstract fun bindSignRepository(repositorySign: SignInRepositoryImpl) : SignInRepository

    @Binds
    abstract fun bindHistoryRepository(repositoryHistory: HistoryRepositoryImpl) : HistoryRepository

    @Binds
    abstract fun bindEditProfileRepository(repositoryEditProfile: EditProfileRepositoryImpl) : EditProfileRepository


}