package com.example.flickrapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrapp.data.PicturesResponse
import com.example.flickrapp.data.model.Picture
import com.example.flickrapp.domain.BuildPictureListUseCase
import com.example.flickrapp.domain.PicturesFlickrUseCase
import com.example.flickrapp.domain.SearchPicturesUseCase
import com.example.flickrapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel

class FlickrViewModel @Inject constructor(
                       private val picturesRepoUserCase: PicturesFlickrUseCase,
                       private val searchPicturesUseCase: SearchPicturesUseCase,
                       private val pictureListUseCase: BuildPictureListUseCase,
                       ) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _selectedPicture = MutableStateFlow<Picture?>(null)
    val selectedPicture = _selectedPicture.asStateFlow()

    private val _pictures = MutableStateFlow<List<Picture>>(emptyList())
    val pictures = _pictures

    private val _uiState = MutableStateFlow<Resource<Any>>(Resource.Loading(null))
    val uiState: StateFlow<Resource<Any>> = _uiState

    init {
        _uiState.update { Resource.Loading(true) }
        onLoadInitData()
    }

    private fun onLoadInitData() {
        if (pictures.value.isEmpty()) {
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
    }
    fun noInfo() {
        _uiState.update { Resource.Success(null) }
    }
    fun isSearching(isSearching: Boolean) {
        _isSearching.update { isSearching }
    }
    fun onSelectItem(picture: Picture) {
        _selectedPicture.update { picture }
    }
    fun onSearchTextChange(text: String) {
//        _isSearching.update { true }
        viewModelScope.launch {
            if (text.isEmpty()) {
//                _pictures.update { picturesRepoUserCase.invoke() }
            } else {
                _pictures.update {  searchPicturesUseCase.invoke(text) }
            }
        }
        _searchText.update { text }
    }
}
