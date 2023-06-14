package com.example.swipe

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.util.readVersion

@Database (entities = [TodoList::class, ItemList::class], version = 1)
internal abstract class MainDb : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        fun getDb(context: Context): MainDb {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                name = "test3.db"
            ).build()
        }
    }
}