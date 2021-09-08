package com.example.primera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.primera.databinding.ActivityListadminBinding
import com.example.primera.databinding.ActivityRegistrarseBinding



class listadmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listadmin)

        var btnCrearTipo = findViewById<Button>(R.id.btnCrearTipo)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val list = resources.getStringArray(R.array.typeArchive)
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        spinner.adapter = adaptador

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        btnCrearTipo.setOnClickListener {
            val intent = Intent(this, regisType::class.java)
            startActivity(intent)
        }
    }
}