package com.glushko.sportcommunity.presentation.matches.model

enum class MatchScreenType {
    TIME_LINE, LISTING;

    companion object {
        fun getScreenType(value: Int?): MatchScreenType {
            return if (value != null && value == 1) {
                TIME_LINE
            } else {
                LISTING
            }
        }
    }
}