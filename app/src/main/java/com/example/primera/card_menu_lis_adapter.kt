package com.example.primera

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class card_menu_lis_adapter (private val card: MutableList<cardStart>, private val types: String) : RecyclerView.Adapter<card_menu_lis_adapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_menu_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cards = card[position]
        if (types == "Organizador") {
            holder.btnConig.isVisible = true
            holder.btnConig.isEnabled = true
        }
        holder.btnConig.setOnClickListener {
            val context= holder.btnConig.context
            val intent = Intent( context, listadmin::class.java)
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = card.size

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgCard)
        val txtTitulo: TextView = itemView.findViewById(R.id.txtTitulo)
        val btnConig: ImageButton = itemView.findViewById(R.id.btnConfig)

    }
}