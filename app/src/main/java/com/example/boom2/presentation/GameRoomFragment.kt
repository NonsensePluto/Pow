package com.example.boom2.presentation

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.boom2.R
import com.example.boom2.data.GameTimer
import com.example.boom2.data.Navigator
import com.example.boom2.data.WordsManager
import com.example.boom2.databinding.ActivityGameRoomBinding

class GameRoomFragment: Fragment(R.layout.activity_game_room) {

    private var binding: ActivityGameRoomBinding? = null
    private val gameViewModel: GameViewModel by activityViewModels()
//    private lateinit var wordsManager: WordsManager

    private lateinit var timerText: TextView
    private lateinit var gameTimer : GameTimer
    private var halfTime = true
    private val time: Long = 30000L


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        wordsManager = WordsManager(this.requireContext())
//        binding = ActivityGameRoomBinding.bind(view)
//        timerText = view.findViewById(R.id.timerText)
//
//        val unguessedWords = gameViewModel.unGuessedWords.value ?: return
//        val guessedWords = gameViewModel.guessedWords.value ?: return
//        val currentTeam = gameViewModel.teams.value?.get(gameViewModel.currTeamNumber.value!! - 1) ?: return
//
//
//        var currWord = unguessedWords.let { wordsManager.selectRandomWord(it) }
//        binding?.wordText?.text = currWord
//
//
//        binding?.pointsButton?.setOnClickListener {
//            currentTeam.wordsCount++
//            guessedWords.add(currWord!!)
//            unguessedWords.remove(currWord)
//            if (unguessedWords.isEmpty()) {
//                currWord = unguessedWords.let { wordsManager.selectRandomWord(it) }
//                binding?.wordText?.text = currWord
//            } else {
//                gameViewModel.endRound.value = true
//                endSession()
//            }
//        }

        var currWord = gameViewModel.randomWord()
        binding?.wordText?.text = currWord

        binding?.pointsButton?.setOnClickListener {
            gameViewModel.guessedWord(currWord!!)
            if (gameViewModel.getSize() != 0) {//почему то до этого стоит проверка на исЭмпти тру
                currWord = gameViewModel.randomWord()
                binding?.wordText?.text = currWord
            } else {
                gameViewModel.endRound()
                endSession()
            }
        }

        startCountdown()
    }

    override fun onDestroy() {
        super.onDestroy()
        gameTimer.stop()
    }


    private fun endSession() {
//        gameViewModel.switchTeam.value = !(halfTime && gameViewModel.endRound.value == true)
        gameViewModel.switchTeam()
        Navigator.navigate(parentFragmentManager, GameLobbyFragment())
    }

    private fun startCountdown() {
        gameTimer = GameTimer(
            totalTime = 30000L,
            intervalTime = 1000L,
            myOnTick = { millisUntilFinished ->
                val seconds = millisUntilFinished / 1000
                timerText.text = String.format("%02d", seconds)
                if (millisUntilFinished <= time / 2 && halfTime) {
                    halfTime = false
                }
            },
            myOnFinish = { endSession() }
        )
        gameTimer.start()

    }
}