package com.glushko.sportcommunity.data.datasource.db

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserStorage @Inject constructor(
    private val sharedPrefs: SharedPreferences
) {

    companion object {
        private const val KEY_LEAGUE_ID = "KEY_LEAGUE_ID"
        private const val KEY_DIVISION_ID = "KEY_DIVISION_ID"
    }

    var favoriteLeagueId: Int
        get() = readLeague()
        set(value) = writeLeague(value)

    private var currentFavoriteDivisionId: Int = -1

    var favoriteDivisionId: Int
        get() = if (currentFavoriteDivisionId != -1) currentFavoriteDivisionId else readDivision()
        set(value) = writeDivision(value)

    fun clearFavorite() = sharedPrefs.edit {
        currentFavoriteDivisionId = -1
        remove(KEY_LEAGUE_ID)
        remove(KEY_DIVISION_ID)
    }

    private fun readLeague(): Int {
        return sharedPrefs.getInt(KEY_LEAGUE_ID, -1)
    }

    private fun writeLeague(id: Int) = sharedPrefs.edit {
        putInt(KEY_LEAGUE_ID, id)
    }

    private fun writeDivision(id: Int) = sharedPrefs.edit {
        currentFavoriteDivisionId = id
        putInt(KEY_DIVISION_ID, id)
    }

    private fun readDivision(): Int {
        val id = sharedPrefs.getInt(KEY_DIVISION_ID, -1)
        currentFavoriteDivisionId = id
        return id
    }
}