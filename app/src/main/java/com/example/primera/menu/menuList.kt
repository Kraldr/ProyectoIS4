package com.example.primera.menu

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.OrientationHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.example.primera.R
import com.example.primera.content.contentClass
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOPIC = "/topics/myTopic"

class menuList : AppCompatActivity() {

    private lateinit var menuAll: Menu
    private lateinit var type: String

    private val database = Firebase.database
    private lateinit var dbref : DatabaseReference
    private lateinit var messagesListener: ValueEventListener
    private lateinit var saveEmail: String
    private val listCard:MutableList<cardStart> = ArrayList()
    private val listCardTop:MutableList<contentClass> = ArrayList()
    private val listBool:MutableList<boolNotify> = ArrayList()
    val myRef = database.getReference("cards")
    private lateinit var item: MenuItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_list)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.elevation = 0F;
        toolbar.setBackgroundColor(Color.parseColor("#ffffff"))

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        saveEmail = sharedPreferences.getString("correo", null).toString()
        type = sharedPreferences.getString("type", null).toString()
        title = "Email: $saveEmail"
        val recycler = findViewById<RecyclerView>(R.id.listRecycler)
        val recyclerTop = findViewById<RecyclerView>(R.id.listRecyclerTop)
        setupRecyclerView(recycler)
        setupRecyclerViewTop(recyclerTop)

        val myService = Intent(this@menuList, MyService::class.java)
        myService.putExtra("inputExtra", "Cosa");
        startService(myService)
        setupBoolNotify ()

    }


    private fun setupRecyclerView(recyclerView: RecyclerView) {
        dbref = FirebaseDatabase.getInstance().getReference("ArchiType")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                listCard.clear()

                if (snapshot.exists()){

                    for (cardSnapshot in snapshot.children){
                        val card = cardSnapshot.getValue(cardStart::class.java)
                        if (card != null) {
                            listCard.add(card)
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

    private fun setupRecyclerViewTop(recyclerView: RecyclerView) {
        dbref = FirebaseDatabase.getInstance().getReference("content")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                listCardTop.clear()

                if (snapshot.exists()){

                    for (cardSnapshot in snapshot.children){
                        val content = cardSnapshot.getValue(contentClass::class.java)
                        if (content != null) {
                            listCardTop.add(content)
                        }
                    }

                    datosTop(recyclerView, listCardTop)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun datosTop (recycler:RecyclerView, all: MutableList<contentClass>) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val types = sharedPreferences.getString("type", null).toString()
        recycler.apply {
            layoutManager = LinearLayoutManager(this@menuList)
            adapter = card_top_adapter(all, this@menuList, types)
        }

    }

    private fun datos (recycler:RecyclerView, all: MutableList<cardStart>) {
        recycler.apply {
            layoutManager = LinearLayoutManager(this@menuList)
            adapter = card_menu_lis_adapter(all, type, applicationContext)
        }

        recycler.layoutManager = LinearLayoutManager(this@menuList, RecyclerView.HORIZONTAL,false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        item = menu.findItem(R.id.ids)
        if (type != "Organizador") {
            item.isVisible = false
        }
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
                val myService = Intent(this@menuList, MyService::class.java)
                stopService(myService)
                finish()
            }

            R.id.ids -> {
                val intent = Intent(this, listadmin::class.java).apply {
                }
                startActivity(intent)
            }
            R.id.infoAdd -> {
                val intent = Intent(this, infoAdd::class.java).apply {
                }
                startActivity(intent)
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

    private fun setupBoolNotify () {
        dbref = FirebaseDatabase.getInstance().getReference("boolNotify")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                listBool.clear()

                if (snapshot.exists()){

                    for (boolNotifySnapshot in snapshot.children){
                        val boolNotifys = boolNotifySnapshot.getValue(boolNotify::class.java)
                        if (boolNotifys != null) {
                            listBool.add(boolNotifys)
                        }
                    }

                    if (listBool[0].boolNoti) {
                        val myService = Intent(this@menuList, MyService::class.java)
                        startService(myService)
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


    private fun startList() {
        val intent = Intent(this, Inicio::class.java).apply {

        }
        startActivity(intent)
        finish()
    }

}