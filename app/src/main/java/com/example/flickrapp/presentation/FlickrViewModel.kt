package com.example.flickrapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrapp.data.model.Picture
import com.example.flickrapp.domain.PicturesFlickrUseCase
import com.example.flickrapp.domain.SearchPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class FlickrViewModel @Inject constructor(
                       private val picturesRepoUserCase: PicturesFlickrUseCase,
                       private val searchPicturesUseCase: SearchPicturesUseCase,
                       ) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()


    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _pictures = MutableStateFlow<List<Picture>>(emptyList())
    val pictures = _pictures
    fun onLoadInitData() {
        if (pictures.value.isEmpty()) {
            _isSearching.update { true }
            viewModelScope.launch {
                _pictures.update { picturesRepoUserCase.invoke() }
                _isSearching.update { false }
            }
        }
    }
    fun onSearchTextChange(text: String) {
        _isSearching.update { true }
        viewModelScope.launch {
            if (text.isEmpty()) {
                _pictures.update { picturesRepoUserCase.invoke() }
            } else {
                _pictures.update {  searchPicturesUseCase.invoke(text) }
            }
            _isSearching.update { false }
        }
        _searchText.update { text }
    }
}
