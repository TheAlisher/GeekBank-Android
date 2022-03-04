package com.alish.geekbank.di

import com.alish.geekbank.data.repositories.SignInRepositoryImpl
import com.alish.geekbank.domain.repositories.SignInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindSignRepository(repositorySignImpl: SignInRepositoryImpl): SignInRepository
}