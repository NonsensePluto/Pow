package com.example.boom2.domain

import com.example.boom2.data.event.Team

data class GameState(
    val teams: List<Team>?,
    var unGuessedWords: MutableList<String>?,
    val guessedWords: MutableList<String> = mutableListOf(),
    var currTeamNumber: Int = START_TEAM,
    var roundsCount: Int = DEFAULT_ROUNDS_COUNT,
    var currRound: Int = DEFAULT_START_ROUND,
    var endRound: Boolean = false,
    var endGame: Boolean = false,
    var switchTeam: Boolean = false
) {

    val currentTeam: Team? get() = teams?.get(currTeamNumber)

    companion object {
        private const val START_TEAM = 1
        private const val DEFAULT_START_ROUND = 1
        private const val DEFAULT_ROUNDS_COUNT = 3
    }
}