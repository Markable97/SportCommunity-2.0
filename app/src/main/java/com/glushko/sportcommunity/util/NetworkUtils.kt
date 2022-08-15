package com.glushko.sportcommunity.util

import android.content.Context
import androidx.annotation.StringRes
import com.glushko.sportcommunity.Application
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.network.UnsuccessfulResponseException
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import javax.inject.Inject

class NetworkUtils @Inject constructor(
    private val retrofit: Retrofit,
    private val appContext: Context,
){
    suspend fun <T> getResponse(request: suspend () -> Response<T>): Resource<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful && result.body() != null) {
                Resource.Success(result.body()!!)
            } else {
                Resource.Error( error = Exception(appContext.getString(R.string.error_network_default)))
            }
        } catch (e: SocketTimeoutException) {
            Timber.e("getResponse $e")
            Resource.Error(error = Exception(appContext.getString(R.string.error_network_default)))
        }catch (e: ConnectException){
            Resource.Error(error = Exception(appContext.getString(R.string.error_network_connection)))
        } catch (e: Exception) {
            Timber.e("getResponse $e")
            Resource.Error(error = Exception(appContext.getString(R.string.error_network_default)))
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