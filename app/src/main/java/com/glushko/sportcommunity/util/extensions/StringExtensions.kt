package com.glushko.sportcommunity.util.extensions

import com.glushko.sportcommunity.util.Constants

fun String.getShortName(): String {
    val nameSplit = this.split(" ")
    return if (nameSplit.size >= 2) {
        "${nameSplit[0]} ${nameSplit[1]}"
    } else {
        this
    }
}

fun String.getFullUrl(): String {
    return "${Constants.LFL_URL}$this"
}