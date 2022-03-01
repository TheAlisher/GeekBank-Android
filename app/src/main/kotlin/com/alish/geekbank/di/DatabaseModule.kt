package com.alish.geekbank.di

import android.content.Context
import com.alish.geekbank.data.local.db.AppDatabase
import com.alish.geekbank.data.local.db.RoomClient
import com.alish.geekbank.data.local.db.daos.FooDao
import com.alish.geekbank.data.local.preferences.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    val roomClient = RoomClient()

    @Singleton
    @Provides
    fun provideRoom(
        @ApplicationContext context: Context
    ): AppDatabase = roomClient.provideRoom(context)

    @Singleton
    @Provides
    fun provideFooDao(
        appDatabase: AppDatabase
    ): FooDao = roomClient.provideFooDao(appDatabase)

    @Provides
    fun providePreferences(@ApplicationContext context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }
}