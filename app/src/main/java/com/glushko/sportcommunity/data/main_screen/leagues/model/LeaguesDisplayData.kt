package com.glushko.sportcommunity.data.main_screen.leagues.model

import com.glushko.sportcommunity.data.main_screen.division.model.DivisionDisplayData

data class LeaguesDisplayData(
    val name: String,
    val id: Int,
    val divisions: List<DivisionDisplayData>
) {
    override fun toString(): String {
        return name
    }
}