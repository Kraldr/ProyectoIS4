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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Inicio : AppCompatActivity() {

    private val database = Firebase.database
    private lateinit var messagesListener: ValueEventListener
    private val listCard:MutableList<cardStart> = ArrayList()
    val myRef = database.getReference("cards")
    private lateinit var menuAll: Menu


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val saveEmail: String = sharedPreferences.getString("correo", null).toString()

        title = "Email: $saveEmail"

        val recycler = findViewById<RecyclerView>(R.id.recyclerCard)
        //prueba2

        setupRecyclerView(recycler)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item -> {
                val intent = Intent(this, MainActivity::class.java).apply {
                }
                saveData("",false,"")
                Toast.makeText(this, "Sesión Cerrada", Toast.LENGTH_LONG).show()
                startActivity(intent)
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        messagesListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listCard.clear()
                dataSnapshot.children.forEach { child ->
                    val card: cardStart? =
                        cardStart(child.child("ID").getValue<String>().toString(),
                            child.child("Title").getValue<String>().toString())
                    card?.let { listCard.add(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "messages:onCancelled: ${error.message}")
            }
        }
        myRef.addValueEventListener(messagesListener)

    }



    private fun saveData (correo:String, online:Boolean, pass: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("correo", correo)
            putString("pass", pass)
            putBoolean("online", online)
        }.apply()
    }


}