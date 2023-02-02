package com.glushko.sportcommunity.data.datasource.network

import java.lang.Exception

class UnsuccessfulResponseException(
    message: String? = null
) : Exception(message)