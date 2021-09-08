package com.example.primera

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.cardview.widget.CardView

class menuList : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_list)

        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val saveEmail: String = sharedPreferences.getString("correo", null).toString()

        title = "Email: $saveEmail"



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

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

    private fun saveData (correo:String, online:Boolean, pass: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("correo", correo)
            putString("pass", pass)
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