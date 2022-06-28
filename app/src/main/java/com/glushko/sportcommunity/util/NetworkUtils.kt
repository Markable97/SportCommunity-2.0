package com.glushko.sportcommunity.util

import androidx.annotation.StringRes
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.network.UnsuccessfulResponseException
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import java.io.IOException
import java.net.SocketException
import javax.inject.Inject

class NetworkUtils @Inject constructor(
    private val retrofit: Retrofit
){
    suspend fun <T> getResponse(@StringRes defaultErrorMessage: Int = R.string.error_network_default, request: suspend () -> Response<T>,): Resource<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                Resource.Success(result.body())
            } else {
                val errorResponse = parseError(result, retrofit)
                Resource.Error(defaultErrorMessage, error = errorResponse)
            }
        } catch (e: SocketException) {
            Resource.Error(R.string.error_network_connection, error = e)
        } catch (e: Exception) {
            Timber.e("getResponse ${e.message}")
            Resource.Error(defaultErrorMessage, error = e)
        }
    }

    private fun parseError(response: Response<*>, retrofit: Retrofit): UnsuccessfulResponseException? {
        val converter = retrofit.responseBodyConverter<UnsuccessfulResponseException>(UnsuccessfulResponseException::class.java, arrayOfNulls(0))
        return try {
            converter.convert(response.errorBody()!!)
        } catch (e: IOException) {
            Timber.e("parseError ${e.message}")
            UnsuccessfulResponseException()
        }
    }
}