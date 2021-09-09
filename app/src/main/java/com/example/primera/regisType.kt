package com.example.primera

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import java.util.*

private lateinit var uniqueID: kotlin.String
private lateinit var type: kotlin.String
private lateinit var dialog: Dialog

class regisType : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis_type)

        var uniqueID = UUID.randomUUID().toString()

        var txtUID = findViewById<EditText>(R.id.txtIDType)
        var btnSubir = findViewById<Button>(R.id.btnRegistrar)

        txtUID.setText(uniqueID)

        btnSubir.setOnClickListener {
            loadSesion()
            crearType()
        }
    }

    private fun loadSesion () {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_progress_bar_with_crear)
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun crearType () {
        var txtUID = findViewById<EditText>(R.id.txtIDType)
        var txtTitle = findViewById<EditText>(R.id.txtTitle)
        var txtUrl = findViewById<EditText>(R.id.txtURLI)
        var UID = txtUID.text.toString()


        val database = FirebaseDatabase.getInstance().getReference("ArchiType")
        val cards = cardStart(UID, txtTitle.text.toString(), txtUrl.text.toString())
        database.child(UID).setValue(cards).addOnSuccessListener {
            Toast.makeText(this, "Tipo de arcivo creado correctamente", Toast.LENGTH_LONG).show()
            dialog.hide()
            finish()
        }

    }
}