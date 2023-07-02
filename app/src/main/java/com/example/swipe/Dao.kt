package com.example.swipe

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertItem(item: TodoList) : Long

    @Insert
    fun insertReminder(reminder: ItemList) : Long
    @Transaction
    @Query("SELECT * FROM todo_list")
    fun getAllItem(): Flow<List<TodoWithItems>>

    @Query("SELECT * FROM todo_list WHERE todoListId == :indexList")
    fun getAllItemsById(indexList : Long) : Array<TodoWithItems>

    @Query("DELETE FROM item_list WHERE itemListId == :deleteId")
    fun deleteItemById(deleteId : Long)

    @Query("DELETE FROM todo_list WHERE todoListId == :index")
    fun deleteListId(index : Long)

    @Query("UPDATE item_list SET item=:newItem WHERE itemListId == :id ")
    fun updateitem(newItem : String, id : Long)

    @Query("SELECT * FROM item_list WHERE itemListId == :todoId")
    fun getTodoItemById(todoId : Long) : ItemList

    @Update
    fun updateItem2(item : ItemList)

     @Query("SELECT * FROM item_list WHERE userCreatorId == :id ORDER BY itemListId ASC LIMIT :limit OFFSET :offset ")
     fun getListPage(id: Long, limit: Int, offset: Int) : List<ItemList>
}