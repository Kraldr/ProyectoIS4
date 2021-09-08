package com.example.primera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class allAdapter ( private val todo: List<allUsers>) : RecyclerView.Adapter<allAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val all = todo[position]

    }

    override fun getItemCount() = todo.size

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val img: TextView = itemView.findViewById(R.id.img)
        val videoV: TextView = itemView.findViewById(R.id.videoV)
        val txtTitulo: TextView = itemView.findViewById(R.id.txtTitulo)
        val txtDescrp: TextView = itemView.findViewById(R.id.txtdescrp)

    }
}