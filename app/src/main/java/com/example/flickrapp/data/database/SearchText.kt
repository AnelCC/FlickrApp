package com.example.flickrapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random


@Entity(tableName = "searchText")
data class SearchText(
    @PrimaryKey val uid: Int? = Random.nextInt(0, 999),
    @ColumnInfo(name = "search_text") val searchText: String
)