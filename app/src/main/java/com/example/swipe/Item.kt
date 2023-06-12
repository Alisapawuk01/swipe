package com.example.swipe

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "items")
data class Item (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "list")
    var list: String,
    @ColumnInfo(name = "mylists")
    var mylists: String,



        )