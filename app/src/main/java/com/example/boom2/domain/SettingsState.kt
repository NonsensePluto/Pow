package com.example.boom2.domain

import com.example.boom2.data.event.Team

data class SettingsState(
    var countOfTeams: Int = 2,
    var countOfWords: Int = 30,
    var hardCoreMode: Boolean = false,
    var forthRound: Boolean = false,
    var teams: List<Team>
) {
}