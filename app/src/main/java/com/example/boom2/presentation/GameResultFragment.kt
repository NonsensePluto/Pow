package com.example.boom2.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boom2.R
import com.example.boom2.data.Navigator
import com.example.boom2.data.WordsManager
import com.example.boom2.data.adapter.ResultAdapter
import com.example.boom2.data.adapter.TeamAdapter
import com.example.boom2.databinding.ActivityGameResultBinding
import com.example.boom2.databinding.ActivitySettingsBinding

class GameResultFragment: Fragment(R.layout.activity_game_result) {

    private val gameViewModel: GameViewModel by activityViewModels()
    private val settingsViewModel: SettingsViewModel by activityViewModels()
    private lateinit var binding: ActivityGameResultBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ActivityGameResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val teamRecycler: RecyclerView = binding.teamResultRecycler
        val teamAdapter = ResultAdapter()

        teamRecycler.layoutManager = LinearLayoutManager(requireContext())
        teamRecycler.adapter = teamAdapter

        teamAdapter.data = gameViewModel.teams.value!!

        binding.resultButton.setOnClickListener {
            backToGame()
        }
    }

    private fun backToGame() {

        if (gameViewModel.roundsCount.value == gameViewModel.currRound.value) {
            gameViewModel.endGame.value = true
        }

        gameViewModel.currRound.value = gameViewModel.currRound.value!! + 1


        if (gameViewModel.endGame.value != true) {

            if (settingsViewModel.hardCoreMode.value == true) {

                val wordsManager = context?.let { WordsManager(it) }
                gameViewModel.unGuessedWords.value = wordsManager?.selectRandomWords(settingsViewModel.countOfWords.value!!)?.toMutableList()

            } else {

                gameViewModel.guessedWords.value?.let {
                    gameViewModel.unGuessedWords.value?.addAll(it)
                }
            }
            gameViewModel.guessedWords.value?.clear()

            gameViewModel.endRound.value = false
            Navigator.navigate(parentFragmentManager, GameLobbyFragment())

        } else {
            gameViewModel.resetGame()
            Navigator.navigate(parentFragmentManager, MainMenuFragment())
        }

    }
}