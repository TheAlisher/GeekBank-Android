package com.alish.geekbank.di

import com.alish.geekbank.data.repositories.CardsRepositoryImpl
import com.alish.geekbank.domain.repositories.FooRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindFooRepository(repositoryImpl: CardsRepositoryImpl): FooRepository
}