package com.example.flickrapp.utils

import androidx.annotation.StringRes

sealed class ErrorText {
    data class DynamicString(val value: String) : ErrorText()
    class StringResource(@StringRes val id: Int, val args: Array<Any> = emptyArray()) : ErrorText()

    fun asString(): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> id.toString()
        }
    }
}