package com.example.flickrapp.domain

import com.example.flickrapp.R
import com.example.flickrapp.data.PicturesResponse
import com.example.flickrapp.data.Repository
import com.example.flickrapp.utils.ErrorText
import com.example.flickrapp.utils.Resource
import javax.inject.Inject

class SearchPicturesUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(search: String) = getPictures(repository.getPictureSearchBy(search))

    private fun getPictures(response: Resource<PicturesResponse>): Resource<PicturesResponse> {
        return when (response) {
            is Resource.Success -> {
                validateEmptyResponse(response.data)
            }
            is Resource.Error -> getError(response.code)
            else -> getError(null)
        }
    }

    private fun validateEmptyResponse(response: PicturesResponse?): Resource<PicturesResponse> {
        return response?.photos?.photo?.let {
            Resource.Success(response)
        }?: getError(ErrorText.StringResource(R.string.empty_list_error))
    }
    private fun getError(errorCode: Any?): Resource<PicturesResponse> {
        return when (errorCode) {
            400 -> Resource.Error(ErrorText.StringResource(R.string.general_key))
            500 -> Resource.Error(ErrorText.StringResource(R.string.general_error))
            else -> Resource.Error(ErrorText.StringResource(R.string.general_error))
        }

    }
}