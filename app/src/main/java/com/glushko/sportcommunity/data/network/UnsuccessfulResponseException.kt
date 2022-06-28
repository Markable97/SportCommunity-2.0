package com.glushko.sportcommunity.data.network

import java.lang.Exception

class UnsuccessfulResponseException(
    val statusCode: Int = 400,
    val success: Boolean = false,
    message: String? = null
) : Exception(message)