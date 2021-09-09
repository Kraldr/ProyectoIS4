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
private var meesage:String = ""

class content : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        meesage = intent.getStringExtra("Type").toString()
        val recycler = findViewById<RecyclerView>(R.id.recyclerContent)
        setupRecyclerView(recycler)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        dbref = FirebaseDatabase.getInstance().getReference("content")
        val listCards:MutableList<contentClass> = ArrayList()
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                listCard.clear()
                listCards.clear()

                if (snapshot.exists()){

                    for (cardSnapshot in snapshot.children){
                        val content = cardSnapshot.getValue(contentClass::class.java)
                        if (content != null) {
                            listCard.add(content)
                        }
                    }

                    for (i in listCard) {
                        if(i.type == meesage) {
                            listCards.add(i)
                        }
                    }

                    datos(recyclerView, listCards)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun datos (recycler:RecyclerView, all: MutableList<contentClass>) {
        recycler.apply {
            layoutManager = LinearLayoutManager(this@content)
            adapter = content_lis_adapter(all,applicationContext)
        }

        recycler.layoutManager = GridLayoutManager(this, 1)
    }
}