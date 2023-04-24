package com.example.flickrapp.utils

sealed class Resource <out T> {
    data class Success<out T>(val data: T?): Resource<T>()
    data class Loading<out T>(val data: T?): Resource<T>()
    data class Error(val message: ErrorText, val code: Int? = null): Resource<Nothing>()
}