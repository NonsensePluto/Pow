package com.example.boom2.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.boom2.data.WordsManager
import com.example.boom2.data.event.Team
import com.example.boom2.data.event.TeamGenerator
import com.example.boom2.domain.GameState
import com.example.boom2.domain.GetSizeUseCase
import com.example.boom2.domain.GuessedWordUseCase
import com.example.boom2.domain.RandomWordUseCase
import com.example.boom2.domain.ResetGameUseCase
import com.example.boom2.domain.StartGameUseCase
import com.example.boom2.domain.SwitchTeamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val startGameUseCase: StartGameUseCase,
    private val guessedWordUseCase: GuessedWordUseCase,
    private val switchTeamUseCase: SwitchTeamUseCase,
    private val resetGameUseCase: ResetGameUseCase,
    private val randomWordUseCase: RandomWordUseCase,
    private val getSizeUseCase: GetSizeUseCase
): ViewModel() {

    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> get() = _gameState

    fun initializeGame(settingsViewModel: SettingsViewModel) {
        val state = startGameUseCase(settingsViewModel)
        _gameState.value = state
    }

    fun guessedWord(word: String) {
        _gameState.value?.let { state ->
            guessedWordUseCase(state, word)
            _gameState.value = state
        }
    }

    fun randomWord(): String? {
        var word: String? = null
        _gameState.value?.let { state->
            word = randomWordUseCase(state)
        }

        return word
    }

    fun switchTeam() {
        _gameState.value?.let { state ->
            switchTeamUseCase(state)
            _gameState.value = state
        }
    }

    fun resetGame() {
        _gameState.value?.let { state ->
            resetGameUseCase(state)
            _gameState.value = state
        }
    }

    fun getSize():Int {
        var size: Int = -1
        _gameState.value?.let { state ->
            size = getSizeUseCase(state)!!
        }

        return size
    }

    fun isNotInit(): Boolean {
        return _gameState.value == null ||
                _gameState.value?.unGuessedWords == null ||
                _gameState.value?.teams == null
    }

    fun endRound() {
        _gameState.value?.let { state->
            state.endRound = true
            _gameState.value = state
        }
    }


//    var currTeamNumber = MutableLiveData(START_TEAM)
//    var roundsCount = MutableLiveData(DEFAULT_ROUNDS_COUNT)
//    var currRound = MutableLiveData(DEFAULT_START_ROUND)
//    var unGuessedWords = MutableLiveData<MutableList<String>?>()
//    var guessedWords = MutableLiveData<MutableList<String>>(mutableListOf())
//    var teams = MutableLiveData<List<Team>?>()
//    var endRound = MutableLiveData(false)
//    var endGame = MutableLiveData(false)
//    var switchTeam = MutableLiveData(false)
//
//
//    fun initializeGame(settingsViewModel: SettingsViewModel, context: Context) {
//        // Инициализация команд
//        if (teams.value == null) {
//            teams.value = settingsViewModel.teams.value ?: TeamGenerator.generateTeam(settingsViewModel.countOfTeams.value!!)
//        }
//
//        // Инициализация списка слов
//        if (unGuessedWords.value == null) {
//            val wordsManager = WordsManager(context)
//            unGuessedWords.value = wordsManager.selectRandomWords(settingsViewModel.countOfWords.value!!).toMutableList()
//        }
//
//        // Установка количества раундов
//        if (settingsViewModel.forthRound.value == true) {
//            roundsCount.value = 4
//        }
//    }
//
//    //добавить проход по командам который будет их обнулять
//    fun resetGame() {
//        currTeamNumber.value = 1
//        roundsCount.value = 3
//        currRound.value = 1
//        unGuessedWords.value = null
//        guessedWords.value?.clear()
//        resetTeams()
//        endRound.value = false
//        endGame.value = false
//        switchTeam.value = false
//    }
//
//    private fun resetTeams() {
//        if (teams.value != null) {
//            teams.value!!.forEach { team ->
//                team.wordsCount = 0
//            }
//            teams.value = null
//        }
//    }
//
//    companion object {
//        private const val START_TEAM = 1
//        private const val DEFAULT_START_ROUND = 1
//        private const val DEFAULT_ROUNDS_COUNT = 3
//    }
}