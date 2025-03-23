package com.example.boom2.presentation

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
import com.example.boom2.data.adapter.TeamAdapter
import com.example.boom2.databinding.ActivitySettingsBinding
import com.example.boom2.data.event.TeamGenerator

class SettingsFragment: Fragment(R.layout.activity_settings) {
    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by activityViewModels()

    private final val MINIMUM_TEAM_COUNT = 2

    private var countOfTeams = MINIMUM_TEAM_COUNT
    private var countOfWords = 30;
    private var hardCoreMode = false
    private var forthRound = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ActivitySettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val teamRecycler: RecyclerView = binding.teamRecycler
        val teamAdapter = TeamAdapter()


        teamRecycler.layoutManager = LinearLayoutManager(requireContext())
        teamRecycler.adapter = teamAdapter

        teamAdapter.data = TeamGenerator.generateTeam(MINIMUM_TEAM_COUNT)

        binding.addTeamButton.setOnClickListener {
            countOfTeams++
            TeamGenerator.pushBack()
            teamAdapter.data = TeamGenerator.getTeams()
            viewModel.teams.value = TeamGenerator.getTeams()
        }

        binding.removeTeamButton.setOnClickListener {
            if(countOfTeams != MINIMUM_TEAM_COUNT) {
                countOfTeams--
                TeamGenerator.removeLast()
                teamAdapter.data = TeamGenerator.getTeams()
                viewModel.teams.value = TeamGenerator.getTeams()
            }
        }

        binding.forthRoundSwitch.setOnCheckedChangeListener { _, isChecked ->
            forthRound = isChecked
        }

        binding.hardcoreRoundSwitch.setOnCheckedChangeListener { _, isChecked ->
            hardCoreMode = isChecked
        }

        binding.WordsButton15.setOnClickListener {
            countOfWords = 15
            val message = "Количество слов: $countOfWords"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        binding.WordsButton30.setOnClickListener {
            countOfWords = 30
            val message = "Количество слов: $countOfWords"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        binding.WordsButton45.setOnClickListener {
            countOfWords = 45
            val message = "Количество слов: $countOfWords"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.countOfTeams.value = countOfTeams
        viewModel.countOfWords.value = countOfWords
        viewModel.forthRound.value = forthRound
        viewModel.hardCoreMode.value = hardCoreMode

        //binding = null
    }
}
