package app.sato.ken.scrtch.adapter

import android.view.View
import android.widget.TextView
import app.sato.ken.scratch_newer_spring_2021.R

class HomeViewHolder(itemView: View) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.simpleText)
//    val historyView: TextView = itemView.findViewById(R.id.historyText)
}