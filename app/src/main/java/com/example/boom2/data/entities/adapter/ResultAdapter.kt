package com.example.boom2.data.entities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.boom2.R
import com.example.boom2.data.entities.Team

class ResultAdapter: RecyclerView.Adapter<ResultAdapter.TeamHolder>() {

    var data: List<Team> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.result_item, parent, false)
        return TeamHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TeamHolder, position: Int) {
        data[position].let {
            holder.teamImage.setImageResource(it.imageId)
            holder.teamName.text = it.name
            holder.teamScore.text = it.wordsCount.toString()
        }
    }

    class TeamHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamImage: ImageView = itemView.findViewById(R.id.teamImage)
        val teamName: TextView = itemView.findViewById(R.id.teamName)
        val teamScore: TextView = itemView.findViewById(R.id.teamScore)
    }
}