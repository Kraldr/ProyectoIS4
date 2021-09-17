package com.example.primera

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*
import android.media.MediaPlayer

import android.media.MediaPlayer.OnPreparedListener





class content_lis_adapter (private val card: MutableList<contentClass>, private val context: Context) : RecyclerView.Adapter<content_lis_adapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cards = card[position]
        holder.txtTitulo.text = cards.title
        holder.txtdescrp.text = cards.descrip

        if (cards.type == "Videos") {
            holder.play.isVisible = true
        }else {
            holder.img.isVisible = true
            Glide.with(holder.itemView.context).load(cards.url).into(holder.img);
        }


        holder.cardActive.setOnClickListener {
            val intent = Intent( context, contentRepro::class.java).apply {
                putExtra("url", cards.url)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = card.size

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val play: ImageView = itemView.findViewById(R.id.play)
        val cardActive: CardView = itemView.findViewById(R.id.imgCard)
        val img: ImageView = itemView.findViewById(R.id.img)
        val txtTitulo: TextView = itemView.findViewById(R.id.txtTitulo)
        val txtdescrp: TextView = itemView.findViewById(R.id.txtdescrp)

    }
}