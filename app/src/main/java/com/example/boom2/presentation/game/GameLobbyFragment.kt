package com.example.boom2.presentation.game

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.boom2.R
import com.example.boom2.domain.Navigator
import com.example.boom2.domain.WordsManager
import com.example.boom2.databinding.ActivityGameLobbyBinding
import com.example.boom2.presentation.settings.SettingsViewModel
import com.example.boom2.presentation.confirms.ConfirmExitFragment


class GameLobbyFragment: Fragment(R.layout.activity_game_lobby) {

    private var binding: ActivityGameLobbyBinding? = null
    private val settingsViewModel: SettingsViewModel by activityViewModels()
    private val gameViewModel: GameViewModel by activityViewModels()
    private lateinit var wordsManager: WordsManager
    private var currentTeamNumber: Int = 1
    private lateinit var navigator: Navigator


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigator = Navigator()
        wordsManager = WordsManager(this.requireContext())
        binding = ActivityGameLobbyBinding.bind(view)
        currentTeamNumber = gameViewModel.currTeamNumber.value!!

        // Инициализация игры (только один раз)
        if (gameViewModel.teams.value == null || gameViewModel.unGuessedWords.value == null) {
            gameViewModel.initializeGame(settingsViewModel, requireContext())
        }


        if (gameViewModel.switchTeam.value == true) {
            switchTeam()
        }

        if (gameViewModel.endRound.value == true) {
            navigator.navigate(parentFragmentManager, GameResultFragment())
        }

        gameViewModel.currTeamNumber.observe(viewLifecycleOwner) { teamNum ->
            val currentTeam = gameViewModel.teams.value?.get(teamNum - 1)
            currentTeam?.let {
                binding?.teamImage?.setImageResource(it.imageId)
            }
        }


        gameViewModel.currRound.observe(viewLifecycleOwner) { currRound->
            binding?.roundText?.text = "Раунд: $currRound"
        }

        gameViewModel.currTeamNumber.observe(viewLifecycleOwner) { currTeamNumber ->
            val currentTeam = gameViewModel.teams.value?.getOrNull(currTeamNumber - 1)
            currentTeam?.let {
                binding?.currentTeamText?.text = "${it.name}"
            }
        }

        gameViewModel.unGuessedWords.observe(viewLifecycleOwner) { unguessedWords ->
            if (unguessedWords != null) {
                binding?.remainingWordsText?.text = "Осталось слов: ${unguessedWords.size}"
            }
        }

        binding?.playButton?.setOnClickListener {
            navigator.navigate(parentFragmentManager, GameRoomFragment())
        }

        // Перехват нажатия кнопки "Назад"
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Показываем диалог подтверждения выхода
                val dialog = ConfirmExitFragment().newInstance()
                dialog.show(parentFragmentManager, "ConfirmExitDialog")
            }
        }

        // Регистрируем обработчик
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    private fun updateTeamImage() {
        val teamNumber = gameViewModel.currTeamNumber.value ?: return
        val currentTeam = gameViewModel.teams.value?.getOrNull(teamNumber - 1)
        currentTeam?.let {
            binding?.teamImage?.setImageResource(it.imageId)
        }
    }

    private fun switchTeam() {
        gameViewModel.currTeamNumber.value = gameViewModel.currTeamNumber.value?.plus(1)
        if (settingsViewModel.countOfTeams.value!! < gameViewModel.currTeamNumber.value!!) {
            gameViewModel.currTeamNumber.value = 1
        }
        gameViewModel.switchTeam.value = false
    }
}