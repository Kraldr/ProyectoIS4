package com.example.primera

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Inicio : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val saveEmail: String = sharedPreferences.getString("correo", null).toString()

        title = "Email: $saveEmail"

        val recycler = findViewById<RecyclerView>(R.id.recycler)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.fecthAllUsers().enqueue(object : Callback<List<allUsers>>{
            override fun onResponse(call: Call<List<allUsers>>, response: Response<List<allUsers>>) {
                datos (recycler, response.body()!!)
            }

            override fun onFailure(call: Call<List<allUsers>>?, t: Throwable?) {
                d("x", "onFailure")
            }

        })



    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, menuList::class.java).apply {

        }
        startActivity(intent)
        finish()
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

    private fun datos (recycler:RecyclerView, all: List<allUsers>) {
        recycler.apply {
            layoutManager = LinearLayoutManager(this@Inicio)
            adapter = allAdapter(all)
        }
    }

}