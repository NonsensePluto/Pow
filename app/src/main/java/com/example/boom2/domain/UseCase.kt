package com.example.boom2.domain

//class StartGameUseCase @Inject constructor(
//    private val wordsManager: WordsManager,
//    private val teamGenerator: TeamGenerator//передача этого в конструктор это принцип DI ЖЭЭЭЭЭ
//){
//    operator fun invoke(settingsViewModel: SettingsViewModel): GameState {
//        val state = settingsViewModel.settingsState.value
//        val teams = teamGenerator.generateTeam(state?.countOfTeams ?: 2)
//        val unGuessedWords = wordsManager.selectRandomWords(state?.countOfWords ?: 30)
//        return GameState(teams, unGuessedWords)
//    }
//}
//
//class GuessedWordUseCase @Inject constructor() {
//    operator fun invoke(gameState: GameState, word: String) {
//        gameState.currentTeam?.incrementWordsCount()
//        gameState.guessedWords.add(word)
//        gameState.unGuessedWords?.remove(word)
//    }
//}
//
//class GetSizeUseCase @Inject constructor() {
//    operator fun invoke(gameState: GameState): Int? {
//        return gameState.unGuessedWords?.size
//    }
//}
//
//class RandomWordUseCase @Inject constructor(
//    private val wordsManager: WordsManager
//) {
//    operator fun invoke(gameState: GameState): String? {
//        return gameState.unGuessedWords?.let { wordsManager.selectRandomWord(it) }
//    }
//}
//
//class SwitchTeamUseCase @Inject constructor() {
//    operator fun invoke(gameState: GameState) {
//        gameState.currTeamNumber++
//        if (gameState.currTeamNumber > gameState.teams?.size!!) {
//            gameState.currTeamNumber = 1
//        }
//    }
//}
//
//class ResetGameUseCase @Inject constructor() {
//    operator fun invoke(gameState: GameState) {
//        gameState.currTeamNumber = 1
//        gameState.roundsCount = 3
//        gameState.currRound = 1
//        gameState.unGuessedWords?.clear()//в оригинале у меня стоит = null но думаю можно просто отчистить ведь теперь
//        // при инициализации проверяется gameState == null
//        gameState.guessedWords.clear()
//        resetTeams(gameState)
//        gameState.endRound = false
//        gameState.endGame = false
//        gameState.switchTeam = false
//    }
//
//    private fun resetTeams(gameState: GameState) {
//        gameState.teams?.forEach { team ->
//            team.wordsCount = 0//добавить потом отдельную функцию и сделать wordsCount private
//        }
//    }
//}
//
//class GenerateDefaultTeamsUseCase @Inject constructor(
//    private val teamGenerator: TeamGenerator
//) {
//    operator fun invoke(count: Int): List<Team> {
//        return teamGenerator.generateTeam(count)
//    }
//}