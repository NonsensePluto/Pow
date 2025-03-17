package com.example.boom2.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.boom2.domain.GetSizeUseCase
import com.example.boom2.domain.GuessedWordUseCase
import com.example.boom2.domain.RandomWordUseCase
import com.example.boom2.domain.ResetGameUseCase
import com.example.boom2.domain.StartGameUseCase
import com.example.boom2.domain.SwitchTeamUseCase
import com.example.boom2.presentation.GameViewModel

class GameViewModelFactory(
    private val startGameUseCase: StartGameUseCase,
    private val guessedWordUseCase: GuessedWordUseCase,
    private val switchTeamUseCase: SwitchTeamUseCase,
    private val resetGameUseCase: ResetGameUseCase,
    private val randomWordUseCase: RandomWordUseCase,
    private val getSizeUseCase: GetSizeUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(
                startGameUseCase,
                guessedWordUseCase,
                switchTeamUseCase,
                resetGameUseCase,
                randomWordUseCase,
                getSizeUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}