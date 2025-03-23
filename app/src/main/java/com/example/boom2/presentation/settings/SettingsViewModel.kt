package com.example.boom2.presentation.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.boom2.data.event.Team
import com.example.boom2.data.event.TeamGenerator


class SettingsViewModel : ViewModel() {

    val countOfTeams = MutableLiveData(MINIMUM_TEAM_COUNT)
    val countOfWords = MutableLiveData(DEFAULT_COUNT_OF_WORDS)
    val hardCoreMode = MutableLiveData(DEFAULT_HARDCORE_STATE)
    val forthRound = MutableLiveData(DEFAULT_FORTH_ROUND_STATE)
    val roundTime = MutableLiveData(DEFAULT_TIME)

    val teams = MutableLiveData<List<Team>>()

    init {
        // Генерация команд по умолчанию
        generateDefaultTeams()
    }

    private fun generateDefaultTeams() {
        teams.value = TeamGenerator.generateTeam(countOfTeams.value ?: 2)
    }

    companion object {
        const val MINIMUM_TEAM_COUNT = 2
        const val DEFAULT_COUNT_OF_WORDS = 30
        const val DEFAULT_HARDCORE_STATE = false
        const val DEFAULT_FORTH_ROUND_STATE = false
        const val DEFAULT_TIME = 30L
    }
}