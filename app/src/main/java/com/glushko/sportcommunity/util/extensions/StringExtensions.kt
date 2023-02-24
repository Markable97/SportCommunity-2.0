package com.glushko.sportcommunity.util.extensions

fun String.getShortName(): String {
    val nameSplit = this.split(" ")
    return if (nameSplit.size >= 2) {
        "${nameSplit[0]} ${nameSplit[1]}"
    } else {
        this
    }
}