package com.example.swipe

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {
    @Insert
    fun InsertItem(item: Item)
    @Query("SELECT * FROM items")
    fun getAllItem(): List<Item>
}