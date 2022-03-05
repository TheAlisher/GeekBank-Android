package com.alish.geekbank.di

import com.alish.geekbank.data.repositories.SignInRepositoryImpl
import com.alish.geekbank.domain.repositories.SignInRepository
import com.alish.geekbank.data.repositories.NewsRepositoryImpl
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
    abstract fun bindSignRepository(repositorySignImpl: SignInRepositoryImpl): SignInRepository
}