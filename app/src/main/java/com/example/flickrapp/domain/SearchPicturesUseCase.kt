package com.example.flickrapp.domain

import com.example.flickrapp.data.PicturesResponse
import com.example.flickrapp.data.Repository
import com.example.flickrapp.data.RepositoryProvider
import com.example.flickrapp.data.model.Picture
import javax.inject.Inject

class SearchPicturesUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(search: String) = getPictures(repository.getPictureSearchBy(search))

    private fun getPictures(response: PicturesResponse?): List<Picture> {
        return response?.let {
            val picturesList = it?.photos?.photo?.map { picture ->
                Picture(
                    id = picture?.id,
                    url = "https://farm${picture?.farm}.staticflickr.com/${picture?.server}/${picture?.id}_${picture?.secret}.jpg",
                    title = picture?.title
                )
            }
            if (picturesList != null) {
                RepositoryProvider.pictures = picturesList
            }
            picturesList
        } ?: emptyList()
    }
}