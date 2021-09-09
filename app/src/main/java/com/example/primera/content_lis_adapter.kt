package com.example.primera

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.widget.VideoView




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
            holder.videV.isVisible = true
            val path1 = cards.url
            val uri: Uri = Uri.parse(path1)
            holder.videV.setVideoURI(uri)
            holder.videV.start()
        }else {
            holder.img.isVisible = true
            Glide.with(holder.itemView.context).load(cards.url).into(holder.img);
        }




        holder.cardActive.setOnClickListener {

        }

    }

    override fun getItemCount() = card.size

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val videV: VideoView = itemView.findViewById(R.id.videoV)
        val cardActive: CardView = itemView.findViewById(R.id.imgCard)
        val img: ImageView = itemView.findViewById(R.id.img)
        val txtTitulo: TextView = itemView.findViewById(R.id.txtTitulo)
        val txtdescrp: TextView = itemView.findViewById(R.id.txtdescrp)

    }
}