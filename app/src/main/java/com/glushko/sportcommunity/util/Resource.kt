package com.glushko.sportcommunity.util

import androidx.annotation.StringRes
import com.glushko.sportcommunity.R

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(val data: T? = null, @StringRes val messages: Int? = null, val error: Exception? = null, val condition: Boolean? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T?, condition: Boolean? = null) : Resource<T>(data = data, condition = condition)
    class Error<T>(@StringRes messages: Int = R.string.error_network_default, data: T? = null, error: Exception? = null) : Resource<T>(data, messages, error)
}

fun <T> Resource<Unit>.toTyped(data: T?) = when (this) {
    is Resource.Loading -> Resource.Loading(data)
    is Resource.Success -> Resource.Success(data, condition = condition)
    is Resource.Error -> Resource.Error(messages!!, data, error)
}
