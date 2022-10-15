package com.glushko.sportcommunity.util

import android.content.Context
import com.glushko.sportcommunity.R
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response
import timber.log.Timber
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class NetworkUtils @Inject constructor(
    private val appContext: Context,
    private val gson: Gson,
) {

    companion object {
        private const val SUCCESS_FIELD = "success"
        private const val MESSAGE_FIELD = "message"
    }

    suspend fun <T> getResponse(request: suspend () -> Response<T>): Resource<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful && result.body() != null) {
                Resource.Success(result.body()!!)
            } else {
                Resource.Error(error = Exception(appContext.getString(R.string.error_network_default)))
            }
        } catch (e: SocketTimeoutException) {
            Timber.e("getResponse $e")
            Resource.Error(error = Exception(appContext.getString(R.string.error_network_default)))
        } catch (e: ConnectException) {
            Resource.Error(error = Exception(appContext.getString(R.string.error_network_connection)))
        } catch (e: Exception) {
            Timber.e("getResponse $e")
            Resource.Error(error = Exception(appContext.getString(R.string.error_network_default)))
        }
    }

    suspend fun <T> getResponseResult(type: Type, request: suspend () -> Response<JsonObject>): Result<T> {
        return try {
            val result = request.invoke()
            val body = result.body()
            if (result.isSuccessful) {
                val success = body?.get(SUCCESS_FIELD)?.asInt
                val message = body?.get(MESSAGE_FIELD)?.asString
                if (success != null) {
                    if (success == 1) {
                        val obj = gson.fromJson<T>(body, type)
                        Result.Success(obj)
                    } else {
                        Result.Error(Exception(message))
                    }
                } else {
                    val obj = gson.fromJson<T>(body, type)
                    Result.Success(obj)
                }
            } else {
                Result.Error(Exception(result.errorBody().toString()))
            }
        } catch (ex: Exception) {
            Timber.e("${ex.message}")
            Result.Error(ex)
        }
    }

//    private fun parseError(response: Response<*>, retrofit: Retrofit): UnsuccessfulResponseException? {
//        val converter = retrofit.responseBodyConverter<UnsuccessfulResponseException>(UnsuccessfulResponseException::class.java, arrayOfNulls(0))
//        return try {
//            converter.convert(response.errorBody()!!)
//        } catch (e: IOException) {
//            Timber.e("parseError ${e.message}")
//            UnsuccessfulResponseException()
//        }
//    }
}