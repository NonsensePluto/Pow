package com.example.boom2.data.event

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.boom2.R
import javax.inject.Inject

class TeamGenerator @Inject constructor() {

    private var teams: MutableList<Team> = mutableListOf()

    private val imgList: List<Int> = listOf(
        R.drawable.bear,
        R.drawable.wolf,
        R.drawable.rabbit,
        R.drawable.turtle,
        R.drawable.cat,
        R.drawable.dog,
        R.drawable.bull
    )

    fun generateTeam(count: Int): List<Team> {
        teams = (1..count).map { index ->
            Team(
                imageId = imgList.random(),
                name = "Команда: $index",
                wordsCount = 0
            )
        }.toMutableList()
        return teams
    }

    fun pushBack() {
        val index = teams.lastOrNull()?.name?.split(" ")?.lastOrNull()?.toIntOrNull()

        val newIndex = index?.let {
            it + 1
        } ?: 1

        val team = Team(imageId = imgList.random(), name = "Команда $newIndex", wordsCount = 0)
        teams.add(team)
    }

    fun removeLast(){
        teams.removeLastOrNull()
    }

    fun getTeams() : List<Team> {
        return teams
    }
}