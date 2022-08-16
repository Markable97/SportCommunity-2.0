package com.glushko.sportcommunity.util

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(val data: T? = null, val error: Exception? = null) {
    class Empty<T>(): Resource<T>()
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(data: T? = null, error: Exception?) : Resource<T>(data, error)
}
