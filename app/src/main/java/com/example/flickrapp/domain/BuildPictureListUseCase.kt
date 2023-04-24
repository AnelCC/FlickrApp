package com.example.flickrapp.domain

import com.example.flickrapp.data.PicturesResponse
import com.example.flickrapp.data.RepositoryProvider
import com.example.flickrapp.data.model.Picture
import com.example.flickrapp.utils.Resource
import javax.inject.Inject

class BuildPictureListUseCase @Inject constructor(){
    operator fun invoke(picturesResponse: Resource<PicturesResponse>) = getPictureList(picturesResponse)
}

fun getPictureList(picturesResponse: Resource<PicturesResponse>): List<Picture> {
    val picturesList = (picturesResponse as Resource.Success).data?.photos?.photo?.map { picture ->
        Picture(
            id = picture?.id,
            url = "https://farm${picture?.farm}.staticflickr.com/${picture?.server}/${picture?.id}_${picture?.secret}.jpg",
            title = picture?.title
        )
    }
    if (picturesList != null) {
        RepositoryProvider.pictures = picturesList
    }
    return picturesList?: emptyList()
}