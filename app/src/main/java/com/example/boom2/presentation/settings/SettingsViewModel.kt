package com.example.boom2.presentation.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.boom2.data.entities.Team
import com.example.boom2.data.entities.event.TeamGenerator


class SettingsViewModel : ViewModel() {
    private val teamGenerator = TeamGenerator

    val countOfTeams = MutableLiveData(MINIMUM_TEAM_COUNT)
    val countOfWords = MutableLiveData(DEFAULT_COUNT_OF_WORDS)
    val hardCoreMode = MutableLiveData(DEFAULT_HARDCORE_STATE)
    val forthRound = MutableLiveData(DEFAULT_FORTH_ROUND_STATE)
    val roundTime = MutableLiveData(DEFAULT_TIME)

    val teams = MutableLiveData<MutableList<Team>>()

    init {
        // Генерация команд по умолчанию
        if (teams.value?.isEmpty() == true || teams.value == null) {
            generateDefaultTeams()
        }
    }

    fun generateDefaultTeams() {
        teams.value = teamGenerator.generateTeam(countOfTeams.value ?: 2).toMutableList()
    }

    companion object {
        const val MINIMUM_TEAM_COUNT = 2
        const val DEFAULT_COUNT_OF_WORDS = 30
        const val DEFAULT_HARDCORE_STATE = false
        const val DEFAULT_FORTH_ROUND_STATE = false
        const val DEFAULT_TIME = 30L
    }
}