package com.example.primera.menu

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.primera.R
import com.example.primera.content.contentClass
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class listadmin : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private val listCard:MutableList<cardStart> = ArrayList()
    private val listTitle:MutableList<String> = ArrayList()
    private lateinit var dialog: Dialog
    private val CHANNEL_ID = "channelTest"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listadmin)

        var btnCrearTipo = findViewById<Button>(R.id.btnCrearTipo)
        var btnRegistro = findViewById<Button>(R.id.btnRegistro)

        setupRecyclerView()


        btnCrearTipo.setOnClickListener {
            val intent = Intent(this, regisType::class.java)
            startActivity(intent)

        }

        btnRegistro.setOnClickListener {
            loadSesion()
            saveData()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item: MenuItem = menu.findItem(R.id.ids)
        item.isVisible = false
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

    private fun loadSesion () {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_progress_bar_with_crear)
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun saveData () {
        var txtTitle = findViewById<EditText>(R.id.txtTitle)
        var txtIMG = findViewById<EditText>(R.id.txtIMG)
        var txtDescrp = findViewById<EditText>(R.id.txtDescrips)
        var UID = UUID.randomUUID().toString()
        val type = findViewById<AutoCompleteTextView>(R.id.typeArchive)

        var number = 1

        val database = FirebaseDatabase.getInstance().getReference("content")
        val databaseBool = FirebaseDatabase.getInstance().getReference("boolNotify")
        val content = contentClass(UID, txtTitle.text.toString(), txtDescrp.text.toString(),type.text.toString(), txtIMG.text.toString())
        val contentBool = boolNotify(number.toString(), true, type.text.toString(), txtTitle.text.toString())
        database.child(UID).setValue(content).addOnSuccessListener {
            Toast.makeText(this, "Contenido agregado correctamente", Toast.LENGTH_LONG).show()
            databaseBool.child(number.toString()).setValue(contentBool).addOnSuccessListener {
            }
            dialog.hide()
            finish()
        }

    }

    private fun setupRecyclerView() {
        dbref = FirebaseDatabase.getInstance().getReference("ArchiType")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                listCard.clear()

                if (snapshot.exists()){

                    for (cardSnapshot in snapshot.children){
                        val card = cardSnapshot.getValue(cardStart::class.java)
                        if (card != null) {
                            listCard.add(card)
                            listTitle.clear()
                            for (i in listCard) {
                                listTitle.add(i.title)
                            }
                            val adapters = ArrayAdapter(applicationContext, R.layout.list_item, listTitle)
                            val text = findViewById<AutoCompleteTextView>(R.id.typeArchive)
                            text.setAdapter(adapters)
                        }
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}