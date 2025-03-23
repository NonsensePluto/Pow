package com.example.boom2.presentation.game

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.boom2.R
import com.example.boom2.data.GameTimer
import com.example.boom2.data.Navigator
import com.example.boom2.data.WordsManager
import com.example.boom2.databinding.ActivityGameRoomBinding
import com.example.boom2.presentation.settings.SettingsViewModel

class GameRoomFragment: Fragment(R.layout.activity_game_room) {

    private var binding: ActivityGameRoomBinding? = null
    private val gameViewModel: GameViewModel by activityViewModels()
    private val settingsViewModel: SettingsViewModel by activityViewModels()
    private lateinit var wordsManager: WordsManager
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayer2: MediaPlayer


    private lateinit var timerText: TextView
    private lateinit var gameTimer : GameTimer
    private var halfTime = true
    private var time: Long = 30000L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wordsManager = WordsManager(this.requireContext())
        mediaPlayer = MediaPlayer.create(this.requireContext(), R.raw.guessed)
        mediaPlayer2 = MediaPlayer.create(this.requireContext(), R.raw.shoot)
        binding = ActivityGameRoomBinding.bind(view)
        timerText = view.findViewById(R.id.timerText)

        time = settingsViewModel.roundTime.value?.times(1000) ?: 0

        val unguessedWords = gameViewModel.unGuessedWords.value ?: return
        val guessedWords = gameViewModel.guessedWords.value ?: return
        val currentTeam = gameViewModel.teams.value?.get(gameViewModel.currTeamNumber.value!! - 1) ?: return


        var currWord = unguessedWords.let { wordsManager.selectRandomWord(it) }
        binding?.wordText?.text = currWord


        binding?.pointsButton?.setOnClickListener {
            currentTeam.wordsCount++
            mediaPlayer.start()
            guessedWords.add(currWord!!)
            unguessedWords.remove(currWord)
            if (unguessedWords.isEmpty() == false) {
                currWord = unguessedWords.let { wordsManager.selectRandomWord(it) }
                binding?.wordText?.text = currWord
            } else {
                gameViewModel.endRound.value = true
                endSession()
            }
        }

//        var currWord = gameViewModel.randomWord()
//        binding?.wordText?.text = currWord
//
//        binding?.pointsButton?.setOnClickListener {
//            gameViewModel.guessedWord(currWord!!)
//            if (gameViewModel.getSize() != 0) {//почему то до этого стоит проверка на исЭмпти тру
//                currWord = gameViewModel.randomWord()
//                binding?.wordText?.text = currWord
//            } else {
//                gameViewModel.endRound()
//                endSession()
//            }
//        }

        startCountdown()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        gameTimer.stop()
//    }


    private fun endSession() {
        if(halfTime && gameViewModel.endRound.value == true) {
            gameViewModel.switchTeam.value = false
        } else {
            gameViewModel.switchTeam.value = true
        }
        mediaPlayer2.start()
        gameTimer.stop()
//        gameViewModel.switchTeam()
        Navigator.navigate(parentFragmentManager, GameLobbyFragment())
    }

    private fun startCountdown() {
        gameTimer = GameTimer(
            totalTime = time,
            intervalTime = 1000L,
            myOnTick = { millisUntilFinished ->
                val seconds = millisUntilFinished / 1000
                when {
                    seconds < 10 -> {
                        timerText.text = "00:0${seconds + 1}"
                    }
                    seconds in 10..58 -> {
                        timerText.text = "00:${seconds + 1}"
                    } seconds.toInt() == 59 -> {
                        timerText.text = "01:00"
                    }

                }
                if (millisUntilFinished <= time - DEFAULT_HALF_TIME && halfTime) {
                    halfTime = false
                }
            },
            myOnFinish = { endSession() }
        )
        gameTimer.start()

    }

    companion object {
        const val DEFAULT_HALF_TIME = 15000L
    }

    override fun onDestroy() {
        super.onDestroy()
        // Освобождаем ресурсы MediaPlayer
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
        if (::mediaPlayer2.isInitialized) {
            mediaPlayer2.release()
        }
    }
}