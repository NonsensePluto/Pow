package com.example.boom2.presentation.game

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.boom2.domain.WordsManager
import com.example.boom2.data.entities.Team
import com.example.boom2.data.entities.event.TeamGenerator
import com.example.boom2.presentation.settings.SettingsViewModel



class GameViewModel : ViewModel(){

    var currTeamNumber = MutableLiveData(START_TEAM)
    var roundsCount = MutableLiveData(DEFAULT_ROUNDS_COUNT)
    var currRound = MutableLiveData(DEFAULT_START_ROUND)
    var unGuessedWords = MutableLiveData<MutableList<String>?>()
    var guessedWords = MutableLiveData<MutableList<String>>(mutableListOf())
    val teams = MutableLiveData<MutableList<Team>?>()
    var endRound = MutableLiveData(false)
    var endGame = MutableLiveData(false)
    var switchTeam = MutableLiveData(false)


    fun initializeGame(settingsViewModel: SettingsViewModel, context: Context) {
        // Инициализация команд
        if (teams.value == null) {
            teams.value = TeamGenerator.generateTeam(settingsViewModel.countOfTeams.value!!).toMutableList()
            teams.value!!.clear()
            teams.value!!.addAll(settingsViewModel.teams.value!!)
        }

        // Инициализация списка слов
        if (unGuessedWords.value == null) {
            val wordsManager = WordsManager(context)
            unGuessedWords.value = wordsManager.selectRandomWords(settingsViewModel.countOfWords.value!!).toMutableList()
        }

        // Установка количества раундов
        if (settingsViewModel.forthRound.value == true) {
            roundsCount.value = 4
        }
    }

    //добавить проход по командам который будет их обнулять
    fun resetGame() {
        currTeamNumber.value = 1
        roundsCount.value = 3
        currRound.value = 1
        unGuessedWords.value = null
        guessedWords.value?.clear()
        resetTeams()
        endRound.value = false
        endGame.value = false
        switchTeam.value = false
    }

    private fun resetTeams() {
        if (teams.value != null) {
            teams.value!!.forEach { team ->
                team.wordsCount = 0
            }
            teams.value = null
        }
    }

    companion object {
        private const val START_TEAM = 1
        private const val DEFAULT_START_ROUND = 1
        private const val DEFAULT_ROUNDS_COUNT = 3
    }
}