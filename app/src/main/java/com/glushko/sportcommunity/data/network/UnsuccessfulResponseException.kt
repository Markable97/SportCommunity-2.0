package com.glushko.sportcommunity.data.network

import java.lang.Exception

class UnsuccessfulResponseException(
    message: String? = null
) : Exception(message)