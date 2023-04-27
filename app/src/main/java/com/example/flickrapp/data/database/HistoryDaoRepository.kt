package com.example.flickrapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDaoRepository {
    fun getSearchList(): Flow<List<SearchText>>
    fun setSearchText(searchText: SearchText): Flow<Unit>
}