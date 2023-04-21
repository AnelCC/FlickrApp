package com.example.flickrapp.data

import com.example.flickrapp.data.model.Picture

class RepositoryProvider {
    companion object {
        var pictures: List<Picture> = emptyList()
    }
}