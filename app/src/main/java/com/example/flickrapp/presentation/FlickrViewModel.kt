package com.example.flickrapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrapp.data.database.SearchText
import com.example.flickrapp.data.model.Picture
import com.example.flickrapp.domain.BuildPictureListUseCase
import com.example.flickrapp.domain.InsertSearchTextUseCase
import com.example.flickrapp.domain.PicturesFlickrUseCase
import com.example.flickrapp.domain.SearchPicturesUseCase
import com.example.flickrapp.domain.SearchTextHistoryListUseCase
import com.example.flickrapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FlickrViewModel @Inject constructor(
    private val pictureListUseCase: BuildPictureListUseCase,
    private val picturesRepoUserCase: PicturesFlickrUseCase,
    private val searchPicturesUseCase: SearchPicturesUseCase,
    private val insertSearchTextUseCase: InsertSearchTextUseCase,
    private val searchTextHistoryListUseCase: SearchTextHistoryListUseCase
    ) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _selectedPicture = MutableStateFlow<Picture?>(null)
    val selectedPicture = _selectedPicture.asStateFlow()

    private val _pictures = MutableStateFlow<List<Picture>>(emptyList())
    val pictures = _pictures

    private val _uiState = MutableStateFlow<Resource<Any>>(Resource.Loading(null))
    val uiState: StateFlow<Resource<Any>> = _uiState

    private val _searchTextList = MutableStateFlow<List<SearchText>>(emptyList())
    val searchList = _searchTextList

    init {
        _uiState.update { Resource.Loading(true) }
        onLoadInitData()
    }

    fun onLoadInitData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when(val result = picturesRepoUserCase.invoke()) {
                    is Resource.Success -> {
                        _pictures.update { pictureListUseCase.invoke(result) }
                        _uiState.update { Resource.Success(result) }
                    }
                    is Resource.Error -> {
                        _uiState.update { Resource.Error(result.message, result.code) }
                    }
                    else -> {}
                }
            }
        }
    }

    suspend fun onSearchPictures(text: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when(val result = searchPicturesUseCase.invoke(text)) {
                    is Resource.Success -> {
                        _pictures.update { pictureListUseCase.invoke(result) }
                        _uiState.update { Resource.Success(result) }
                    }
                    is Resource.Error -> {
                        _uiState.update { Resource.Error(result.message, result.code) }
                    }
                    else -> {}
                }
            }
        }
    }

    fun noInfo() {
        _uiState.update { Resource.Success(null) }
    }

    fun onSelectItem(picture: Picture) {
        _selectedPicture.update { picture }
    }
    fun onSaveSearchText(text: String) {
        viewModelScope.launch {
            if (text.isNotBlank() && !isTextAlreadyInserted(text)) {
                insertSearchTextUseCase.invoke(SearchText(searchText = text))
                    .flowOn(Dispatchers.IO)
                    .catch {
                        Log.d("onSaveSearchText", "{${it.message}}")
                    }.collect { values ->
                        Log.d("onSaveSearchText", "Done: $values")
                    }
            }
        }
    }

    private fun isTextAlreadyInserted(text: String): Boolean {
        return if (_searchTextList.value.isEmpty()) {
            false
        } else {
            val searchText = _searchTextList.value.find {
                it.searchText == text
            }
            searchText != null
        }
    }

    fun getSearchHistory() {
        viewModelScope.launch {
            searchTextHistoryListUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d("getSearchHistory", "{${it.message}}")
                }
                .collect { searchList ->
                    Log.d("here", "getSearchHistory: $searchList")
                    _searchTextList.update {
                        searchList.reversed().filter {
                            it.searchText.contains(searchText.value)
                        }.safeSubList(0,3)
                    }
                }
        }
    }

    fun onSearchTextChange(text: String) {
        _uiState.update { Resource.Loading(true) }

        viewModelScope.launch {
            if (text.isEmpty()) {
                onLoadInitData()
            } else {
                onSearchPictures(text)
            }
        }
        _searchText.update { text }
    }

    fun resetTextChanged() {
        _searchText.update { "" }
        onLoadInitData()
    }
}

fun <T> List<T>.safeSubList(fromIndex: Int, toIndex: Int): List<T> =
    this.subList(fromIndex, toIndex.coerceAtMost(this.size))