package com.example.primera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class card_menu_lis_adapter (private val card: List<allUsers>) : RecyclerView.Adapter<card_menu_lis_adapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_menu_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cards = card[position]

    }

    override fun getItemCount() = card.size

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val img: TextView = itemView.findViewById(R.id.img)
        val txtTitulo: TextView = itemView.findViewById(R.id.txtTitulo)

    }
}