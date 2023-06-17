package com.example.swipe

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertItem(item: TodoList) : Long

    @Insert
    fun insertReminder(vararg reminder: ItemList)
    @Transaction
    @Query("SELECT * FROM todo_list")
    fun getAllItem(): Flow<List<TodoWithItems>>

    @Query("SELECT * FROM todo_list WHERE todoListId == :indexList")
    fun getAllItemsById(indexList : Long) : Array<TodoWithItems>
}