package com.example.boom2.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boom2.R
import com.example.boom2.data.entities.adapter.TeamAdapter
import com.example.boom2.databinding.ActivitySettingsBinding
import com.example.boom2.data.entities.event.TeamGenerator

class SettingsFragment: Fragment(R.layout.activity_settings) {
    private lateinit var binding: ActivitySettingsBinding
    private val settingsViewModel: SettingsViewModel by activityViewModels()
    private val teamGenerator = TeamGenerator


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ActivitySettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val teamRecycler: RecyclerView = binding.teamRecycler
        val teamAdapter = TeamAdapter()
        settingsViewModel.countOfTeams.value = MINIMUM_TEAM_COUNT


        //Команды
        teamRecycler.layoutManager = LinearLayoutManager(requireContext())
        teamRecycler.adapter = teamAdapter

        teamAdapter.data = teamGenerator.generateTeam(MINIMUM_TEAM_COUNT)
        settingsViewModel.teams.value = teamAdapter.data.toMutableList()

        binding.addTeamButton.setOnClickListener {
            settingsViewModel.countOfTeams.value = settingsViewModel.countOfTeams.value!! + 1
            TeamGenerator.pushBack()
            teamAdapter.data = teamGenerator.getTeams()
            settingsViewModel.teams.value = teamGenerator.getTeams().toMutableList()

        }

        binding.removeTeamButton.setOnClickListener {
            if(settingsViewModel.countOfTeams.value != MINIMUM_TEAM_COUNT) {
                settingsViewModel.countOfTeams.value = settingsViewModel.countOfTeams.value!! - 1
                TeamGenerator.removeLast()
                teamAdapter.data = teamGenerator.getTeams()
                settingsViewModel.teams.value = teamGenerator.getTeams().toMutableList()
            }
        }


        //Переключатели
        binding.forthRoundSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.forthRound.value = isChecked
        }

        binding.hardcoreRoundSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.hardCoreMode.value = isChecked
        }


        //Количество слов
        binding.WordsButton15.setOnClickListener {
            val countOfWords = 15
            settingsViewModel.countOfWords.value = countOfWords
            val message = "Количество слов: $countOfWords"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        binding.WordsButton30.setOnClickListener {
            val countOfWords = 30
            settingsViewModel.countOfWords.value = countOfWords
            val message = "Количество слов: $countOfWords"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        binding.WordsButton45.setOnClickListener {
            val countOfWords = 45
            settingsViewModel.countOfWords.value = countOfWords
            val message = "Количество слов: $countOfWords"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        //Время
        binding.timeButton30.setOnClickListener {
            val roundTime = 30L
            settingsViewModel.roundTime.value = roundTime
            val message = "Время раунда: $roundTime"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        binding.timeButton45.setOnClickListener {
            val roundTime = 45L
            settingsViewModel.roundTime.value = roundTime
            val message = "Время раунда: $roundTime"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        binding.timeButton60.setOnClickListener {
            val roundTime = 60L
            settingsViewModel.roundTime.value = roundTime
            val message = "Время раунда: $roundTime"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        const val MINIMUM_TEAM_COUNT = 2
    }
}


