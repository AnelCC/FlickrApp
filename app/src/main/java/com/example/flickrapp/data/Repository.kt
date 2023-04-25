package com.example.flickrapp.data

import retrofit2.http.Query
import javax.inject.Inject

/*Local save data*/
class Repository @Inject constructor(private val  api: ManagerService) {

    suspend fun getAllPictures(): PicturesResponse? {
        return api.getAllPictures()
    }

    suspend fun getPictureSearchBy(query: String): PicturesResponse? {
        return api.getPictureSearchBy(query)
    }
}
