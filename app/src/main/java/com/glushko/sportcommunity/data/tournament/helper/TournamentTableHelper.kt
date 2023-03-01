package com.glushko.sportcommunity.data.tournament.helper

import com.glushko.sportcommunity.data.tournament.repository.TournamentRepositoryImpl
import com.glushko.sportcommunity.presentation.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.util.Resource
import timber.log.Timber

object TournamentTableHelper {

    private const val COUNT_FOR_TABLE = 4

    fun getTournamentTableWithPositionTeam(
        table: List<TournamentTableDisplayData>,
        teamId: Int
    ) : List<TournamentTableDisplayData>{
        val team = table.find { it.teamId == teamId } ?: return emptyList()
        val indexTeam = table.indexOf(team)
        Timber.tag("MarkDev").d("indexTeam = $indexTeam")
        return when {
            indexTeam < COUNT_FOR_TABLE -> {
                table.take(TournamentRepositoryImpl.COUNT_FOR_TABLE)
            }
            table.size - COUNT_FOR_TABLE < indexTeam -> {
                table.takeLast(TournamentRepositoryImpl.COUNT_FOR_TABLE)
            }
            else -> {
                val newList = mutableListOf<TournamentTableDisplayData>()
                for (position in indexTeam - 1 until indexTeam + TournamentRepositoryImpl.COUNT_FOR_TABLE){
                    newList.add(table[position])
                }
                newList
            }
        }
    }

}