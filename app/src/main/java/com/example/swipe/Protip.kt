package com.example.swipe

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "todo_list")
data class TodoList(
    @PrimaryKey(autoGenerate = true)
    val todoListId: Long = 0,
    val name: String
)

@Entity(tableName = "item_list")
data class ItemList(
    @PrimaryKey(autoGenerate = true)
    val itemListId: Long = 0,
    val userCreatorId: Long,
    val item: String
)

data class TodoWithItems(
    @Embedded val todoList: TodoList,
    @Relation(
        parentColumn = "todoListId",
        entityColumn = "userCreatorId"
    )
    val textlist: List<ItemList>
)
