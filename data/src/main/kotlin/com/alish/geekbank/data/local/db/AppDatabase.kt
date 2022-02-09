package com.alish.geekbank.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alish.geekbank.data.local.db.daos.FooDao
import com.alish.geekbank.data.local.db.entities.FooEntity

@Database(entities = [FooEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun fooDao(): FooDao
}