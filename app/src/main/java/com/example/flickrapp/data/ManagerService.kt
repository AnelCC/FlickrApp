package com.example.flickrapp.data

import com.example.flickrapp.core.ApiService
import com.example.flickrapp.utils.Resource
import com.example.flickrapp.utils.ErrorText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ManagerService @Inject constructor(private val api: ApiService) {
    suspend fun getAllPictures() : Resource<PicturesResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getAllPictures()
                if(response.isSuccessful) {
                    Resource.Success(response.body())
                }  else {
                    Resource.Error(ErrorText.DynamicString("Server error" ), response.code())
                }
            } catch (error: Exception) {
                Resource.Error(ErrorText.DynamicString(error.message.toString()))
            }
        }
    }

    suspend fun getPictureSearchBy(query: String) : PicturesResponse? {
        return withContext(Dispatchers.IO) {
            val response = api.getPictureByTag(query)
            if(response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}
