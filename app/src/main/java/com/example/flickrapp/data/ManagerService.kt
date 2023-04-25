package com.example.flickrapp.data

import com.example.flickrapp.core.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ManagerService @Inject constructor(private val retrofit: ApiService) {
    suspend fun getAllPictures() : PicturesResponse? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.getAllPictures()
            if(response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun getPictureSearchBy(query: String) : PicturesResponse? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.getPictureByTag(query)
            if(response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}
