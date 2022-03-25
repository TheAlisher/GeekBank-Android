package com.alish.geekbank.di

import com.alish.geekbank.data.repositories.ExchangeRepositoriesImpl
import com.alish.geekbank.data.repositories.FireStoreRepositoryImpl
import com.alish.geekbank.data.repositories.NewsRepositoryImpl
import com.alish.geekbank.domain.repositories.ExchangeRepository
import com.alish.geekbank.domain.repositories.FirestoreRepository
import com.alish.geekbank.domain.repositories.NewsRepository
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
    abstract fun bindSignRepository(repositoryFirestoreImpl: FireStoreRepositoryImpl) : FirestoreRepository

}