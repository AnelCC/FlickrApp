package com.example.flickrapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM searchText")
    fun getAll(): Flow<List<SearchText>>

    @Query("SELECT * FROM searchText WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): Flow<List<SearchText>>

    @Query("SELECT * FROM searchText WHERE search_text LIKE :searchText LIMIT 10")
    fun findByText(searchText: String): SearchText

    @Upsert
    fun insertText(vararg searchText: SearchText)

    @Delete
    fun delete(searchText: SearchText)
}