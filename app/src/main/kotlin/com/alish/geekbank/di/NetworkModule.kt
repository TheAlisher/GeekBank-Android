package com.alish.geekbank.di

import com.alish.geekbank.data.remote.RetrofitClient
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    val retrofitClient = RetrofitClient()

    @Singleton
    @Provides
    fun provideNewsApiService() = retrofitClient.provideNewsApiService()

    @Singleton
    @Provides
    fun provideFireStore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideExchange() = retrofitClient.provideExchangeApiService()

    @Singleton
    @Provides
    fun provideFireStorage() = FirebaseStorage.getInstance()

    @Singleton
    @Provides
    fun provideStorageReference() = FirebaseStorage.getInstance().reference
}