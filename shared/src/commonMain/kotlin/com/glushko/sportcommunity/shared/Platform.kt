package com.glushko.sportcommunity.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform