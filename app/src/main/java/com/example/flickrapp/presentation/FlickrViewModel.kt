package com.example.flickrapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class FlickrViewModel(): ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()


    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _picture = MutableStateFlow(pictureList)
    val pictures = searchText
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_picture) { text, pictureMatches ->
            if(text.isBlank()) {
                pictureMatches
            } else {
                delay(500L)
                pictureMatches.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(1000),
            _picture.value
        )
    fun onSearchTextChange(text: String) {
        _searchText.update { text }
    }
}

private val pictureList = listOf(
    Picture(
        name = "sunset",
    ),
    Picture(
        name = "forest",
    ),
    Picture(
        name = "dog",
    ),
    Picture(
        name = "food",
    ),
    Picture(
        name = "travel",
    ),
    Picture(
        name = "computer",
    ),
    Picture(
        name = "cat",
    ),
    Picture(
        name = "shoes",
    ),
    Picture(
        name = "selfie",
    ),
    Picture(
        name = "beach",
    ),
    Picture(
        name = "house",
    ),
    Picture(
        name = "cars",
    ),
)

data class Picture(
    val name: String,
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$name",
            "${name.first()}}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
