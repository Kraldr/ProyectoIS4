package com.example.primera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.primera.databinding.ActivityListadminBinding
import com.example.primera.databinding.ActivityRegistrarseBinding



class listadmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listadmin)

        var btnCrearTipo = findViewById<Button>(R.id.btnCrearTipo)

        btnCrearTipo.setOnClickListener {
            val intent = Intent(this, regisType::class.java)
            startActivity(intent)
        }
    }
}