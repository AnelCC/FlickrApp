package com.example.flickrapp.core

import com.example.flickrapp.data.PicturesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("?method=$METHOD_RECENT&api_key=$API_KEY&per_page=$PAGING_LIMIT&format=$JSON$CALLBACK")
    suspend fun getAllPictures(
    ): Response<PicturesResponse?>

    @GET("?method=$METHOD_SEARCH&api_key=$API_KEY&per_page=$PAGING_LIMIT_SEARCH&format=$JSON$CALLBACK")
    suspend fun getPictureByTag(
        @Query("text") tag: String
    ):  Response<PicturesResponse?>

}
