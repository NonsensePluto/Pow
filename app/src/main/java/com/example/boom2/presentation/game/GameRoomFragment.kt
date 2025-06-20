package com.example.boom2.presentation.game


import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.boom2.R
import com.example.boom2.domain.GameTimer
import com.example.boom2.domain.Navigator
import com.example.boom2.domain.WordsManager
import com.example.boom2.databinding.ActivityGameRoomBinding
import com.example.boom2.domain.SoundManager
import com.example.boom2.presentation.settings.SettingsViewModel

class GameRoomFragment: Fragment(R.layout.activity_game_room) {

    private var binding: ActivityGameRoomBinding? = null
    private val gameViewModel: GameViewModel by activityViewModels()
    private val settingsViewModel: SettingsViewModel by activityViewModels()
    private lateinit var soundManager: SoundManager
    private lateinit var wordsManager: WordsManager
    private lateinit var navigator: Navigator



    private lateinit var timerText: TextView
    private lateinit var gameTimer : GameTimer
    private var wordsInSession = 0
    private var halfTime = true
    private var time: Long = 30000L

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Ничего не делаем - просто игнорируем
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigator = Navigator()
        binding = ActivityGameRoomBinding.bind(view)
        timerText = view.findViewById(R.id.timerText)
        soundManager = SoundManager(this.requireContext())
        wordsManager = WordsManager(this.requireContext())

        time = settingsViewModel.roundTime.value?.times(1000) ?: 0

        val unguessedWords = gameViewModel.unGuessedWords.value ?: return
        val guessedWords = gameViewModel.guessedWords.value ?: return
        val currentTeam = gameViewModel.teams.value?.get(gameViewModel.currTeamNumber.value!! - 1) ?: return


        var currWord = unguessedWords.let { wordsManager.selectRandomWord(it) }
        binding?.wordText?.text = currWord


        binding?.pointsButton?.setOnClickListener {
            currentTeam.wordsCount++
            wordsInSession++
            soundManager.playGuessedSound()

            guessedWords.add(currWord!!)
            unguessedWords.remove(currWord)
            if (unguessedWords.isNotEmpty()) {
                currWord = unguessedWords.let { wordsManager.selectRandomWord(it) }
                binding?.wordText?.text = currWord
            } else {
                gameViewModel.endRound.value = true
                endSession()
            }
        }

        binding?.backTrackButton?.setOnClickListener {
            if(wordsInSession > 0) {
                currentTeam.wordsCount--
                soundManager.playBacktrackSound()
                wordsInSession--
                currWord = guessedWords.last()
                binding?.wordText?.text = currWord
                guessedWords.remove(currWord)
                unguessedWords.add(currWord!!)
            } else {
                Toast.makeText(this.requireContext(), "Вы еще не угадали ни одного слова", Toast.LENGTH_SHORT).show()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )

        startCountdown()
    }


    private fun endSession() {
        if(halfTime && gameViewModel.endRound.value == true) {
            gameViewModel.switchTeam.value = false
        } else {
            gameViewModel.switchTeam.value = true
        }
        soundManager.playEndTimeSound()
        wordsInSession = 0
        gameTimer.stop()
        navigator.navigate(parentFragmentManager, GameLobbyFragment())
    }

    private fun startCountdown() {
        gameTimer = GameTimer(
            totalTime = time,
            intervalTime = 1000L,
            myOnTick = { millisUntilFinished ->
                val seconds = millisUntilFinished / 1000
                when {
                    seconds < 9 -> {
                        timerText.text = "00:0${seconds + 1}"
                    }
                    seconds in 9..58 -> {
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
        soundManager.release()
    }
}