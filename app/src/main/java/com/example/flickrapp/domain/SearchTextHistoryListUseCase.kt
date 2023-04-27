package com.example.flickrapp.domain

import com.example.flickrapp.data.database.SearchHistoryRepository
import com.example.flickrapp.data.database.SearchText
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTextHistoryListUseCase @Inject constructor(private val repositoryDB: SearchHistoryRepository) {

    suspend operator fun invoke(): Flow<List<SearchText>> {
        return repositoryDB.getSearchList()
    }
}

