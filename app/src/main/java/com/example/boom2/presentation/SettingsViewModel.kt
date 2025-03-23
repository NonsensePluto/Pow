package com.example.boom2.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.boom2.data.event.Team
import com.example.boom2.data.event.TeamGenerator


class SettingsViewModel : ViewModel() {

    val countOfTeams = MutableLiveData<Int>(2)//при изменении автоматически изменятся в отображении поэтому MutableLiveData
    val countOfWords = MutableLiveData<Int>(30)//вынести все в конастанты
    val hardCoreMode = MutableLiveData<Boolean>(false)
    val forthRound = MutableLiveData<Boolean>(false)

    val teams = MutableLiveData<List<Team>>()

    init {
        // Генерация команд по умолчанию
        generateDefaultTeams()
    }

    private fun generateDefaultTeams() {
        teams.value = TeamGenerator.generateTeam(countOfTeams.value ?: 2)
    }
}