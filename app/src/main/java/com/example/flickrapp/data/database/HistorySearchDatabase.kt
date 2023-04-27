package com.example.flickrapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SearchText::class],
    version = 1
)
abstract class HistorySearchDatabase: RoomDatabase() {
    abstract val dao: HistoryDao
}