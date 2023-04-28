package com.example.flickrapp.data.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchHistoryRepository @Inject constructor(private val historyDao: HistoryDao): HistoryDaoRepository  {
    override fun getSearchList(): Flow<List<SearchText>> {
        return historyDao.getAll()
    }

    override fun setSearchText(searchText: SearchText): Flow<Unit> = flow {
        historyDao.insertText(searchText)
        emit(Unit)
    }
}