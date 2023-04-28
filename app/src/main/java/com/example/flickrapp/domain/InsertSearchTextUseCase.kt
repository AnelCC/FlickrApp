package com.example.flickrapp.domain

import com.example.flickrapp.data.database.SearchHistoryRepository
import com.example.flickrapp.data.database.SearchText
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertSearchTextUseCase @Inject constructor(private val repositoryDB: SearchHistoryRepository) {

    suspend operator fun invoke(searchText: SearchText): Flow<Unit> {
        return repositoryDB.setSearchText(searchText)
    }
}

