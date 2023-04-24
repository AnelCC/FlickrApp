package com.example.flickrapp.data

import com.example.flickrapp.utils.Resource
import retrofit2.http.Query
import javax.inject.Inject

/*Local save data*/
class Repository @Inject constructor(private val  managerService: ManagerService) {

    suspend fun getAllPictures(): Resource<PicturesResponse> {
        return managerService.getAllPictures()
    }

    suspend fun getPictureSearchBy(query: String): Resource<PicturesResponse> {
        return managerService.getPictureSearchBy(query)
    }
}
