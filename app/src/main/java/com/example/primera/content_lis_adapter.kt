package com.example.primera

import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class content_lis_adapter (private val card: MutableList<cardStart>, private val types: String, private val context: Context) : RecyclerView.Adapter<content_lis_adapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_menu_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cards = card[position]
        Glide.with(holder.itemView.context).load(cards.urli).into(holder.img);
        holder.txtTitulo.text = cards.title

        holder.cardActive.setOnClickListener {
            val intent = Intent( context, content::class.java).apply {
                putExtra("Type", "Holita")
            }
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = card.size

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val cardActive: CardView = itemView.findViewById(R.id.cardMenu)
        val img: ImageView = itemView.findViewById(R.id.imgCard)
        val txtTitulo: TextView = itemView.findViewById(R.id.txtTitulo)

    }
}