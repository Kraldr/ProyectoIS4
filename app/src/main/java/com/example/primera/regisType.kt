package com.example.primera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import java.util.*

private lateinit var uniqueID: kotlin.String
private lateinit var type: kotlin.String

class regisType : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis_type)

        var uniqueID = UUID.randomUUID().toString()

        var txtUID = findViewById<EditText>(R.id.txtIDType)
        var btnSubir = findViewById<Button>(R.id.btnRegistrar)

        txtUID.setText(uniqueID)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val list = resources.getStringArray(R.array.typeArchive)
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        spinner.adapter = adaptador

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                type = list[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        btnSubir.setOnClickListener {
            crearType()
        }
    }

    private fun crearType () {
        var txtUID = findViewById<EditText>(R.id.txtIDType)
        var txtTitle = findViewById<EditText>(R.id.txtTitle)
        var UID = txtUID.text.toString()


        val database = FirebaseDatabase.getInstance().getReference("ArchiType")
        val cards = cardStart(UID, txtTitle.text.toString() , type)
        database.child(UID).setValue(cards).addOnSuccessListener {}
    }
}