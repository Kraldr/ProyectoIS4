package com.example.primera

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.example.primera.cardStart

class menuList : AppCompatActivity() {

    private lateinit var menuAll: Menu
    private lateinit var type: String

    private val database = Firebase.database
    private lateinit var dbref : DatabaseReference
    private lateinit var messagesListener: ValueEventListener
    private lateinit var saveEmail: String
    val myRef = database.getReference("cards")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_list)

        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        saveEmail = sharedPreferences.getString("correo", null).toString()
        type = sharedPreferences.getString("type", null).toString()
        title = "Email: $saveEmail"
        val recycler = findViewById<RecyclerView>(R.id.listRecycler)
        setupRecyclerView(recycler)

    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        dbref = FirebaseDatabase.getInstance().getReference("cards")
        val listCard:MutableList<cardStart> = ArrayList()
        listCard.clear()
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (cardSnapshot in snapshot.children){
                        val card = cardSnapshot.getValue(cardStart::class.java)
                        if (card != null) {
                            listCard.add(card)
                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        var cards = cardStart("20", "Carta","Cos")
        listCard.add(0, cards)
        listCard.add(1, cards)
        datos(recyclerView, listCard)

    }

    private fun datos (recycler:RecyclerView, all: MutableList<cardStart>) {
        Toast.makeText(this, type, Toast.LENGTH_LONG).show()
        recycler.apply {
            layoutManager = LinearLayoutManager(this@menuList)
            adapter = card_menu_lis_adapter(all, type)
        }

        recycler.layoutManager = GridLayoutManager(this, 2)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menuAll = menu

        /* val value :MenuItem = menuAll.findItem()
        if (value != null && type == "Persona" || value != null && type == "Empresa") {
            value.isVisible = false
        }else if (type == "Organizador") {
            value.isVisible = true
        }*/

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item -> {
                val intent = Intent(this, MainActivity::class.java).apply {
                }
                saveData("sincorreo",false,"falso")
                Toast.makeText(this, "Sesión Cerrada", Toast.LENGTH_LONG).show()
                startActivity(intent)
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveData (correo:String, online:Boolean, type: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("correo", correo)
            putString("type", type)
            putBoolean("online", online)
        }.apply()
    }


    private fun startList() {
        val intent = Intent(this, Inicio::class.java).apply {

        }
        startActivity(intent)
        finish()
    }

}