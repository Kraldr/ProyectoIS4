package com.example.primera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

private lateinit var dbref : DatabaseReference
private val listCard:MutableList<contentClass> = ArrayList()

class content : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val meesage:String = intent.getStringExtra("Type").toString()

        Toast.makeText(applicationContext, meesage , Toast.LENGTH_LONG).show()
    }

    /*private fun setupRecyclerView(recyclerView: RecyclerView) {
        dbref = FirebaseDatabase.getInstance().getReference("content")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                listCard.clear()

                if (snapshot.exists()){

                    for (cardSnapshot in snapshot.children){
                        val content = cardSnapshot.getValue(contentClass::class.java)
                        if (content != null) {
                            listCard.add(content)
                        }
                    }

                    datos(recyclerView, listCard)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun datos (recycler:RecyclerView, all: MutableList<cardStart>) {
        recycler.apply {
            layoutManager = LinearLayoutManager(this@menuList)
            adapter = card_menu_lis_adapter(all, type, applicationContext)
        }

        recycler.layoutManager = GridLayoutManager(this, 1)
    }*/
}