package com.example.flickrapp.data.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.flickrapp.data.model.Picture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
/*

class ConversationMapper {
    // Maps ConversationEntity to Conversation
    fun SearchText.mapToConversation(): SearchText {
        // I have no idea of the fields, so you have to implement this mapping function yourself.
        return SearchText(...)
    }
}*/
