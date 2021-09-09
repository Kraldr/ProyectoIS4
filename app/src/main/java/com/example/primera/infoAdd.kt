package com.example.primera

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.*

class infoAdd : AppCompatActivity() {

    private var saveKey:String = ""
    private lateinit var dbref : DatabaseReference
    private val listUser:MutableList<allUsers> = ArrayList()
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_add)

        val btnGuardar: Button = findViewById(R.id.btnGuardar)
        val txtCC = findViewById<EditText>(R.id.txtCC)
        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtApellido = findViewById<EditText>(R.id.txtApellido)
        val txtCel = findViewById<EditText>(R.id.txtCel)

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        saveKey = sharedPreferences.getString("key", null).toString()


        btnGuardar.setOnClickListener {
            if(btnGuardar.text == "GUARDAR DATOS") {
                loadSesion()
                saveData()
                btnGuardar.text = "ACTUALIZAR DATOS"
                txtCC.isEnabled = false
                txtNombre.isEnabled = false
                txtApellido.isEnabled = false
                txtCel.isEnabled = false
            }else {

                txtCC.isEnabled = true
                txtNombre.isEnabled = true
                txtApellido.isEnabled = true
                txtCel.isEnabled = true
                btnGuardar.text = "GUARDAR DATOS"
            }

        }

        setupRecyclerView()
    }

    private fun saveData() {
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtCC = findViewById<EditText>(R.id.txtCC)
        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtApellido = findViewById<EditText>(R.id.txtApellido)
        val txtCel = findViewById<EditText>(R.id.txtCel)
        val txtTypeAccount = findViewById<EditText>(R.id.txtTypeAccount)


        val database = FirebaseDatabase.getInstance().getReference("users")
        val users = allUsers(saveKey, txtEmail.text.toString(), txtCC.text.toString() , txtNombre.text.toString(),txtApellido.text.toString(), txtCel.text.toString() , txtTypeAccount.text.toString())
        database.child(saveKey).setValue(users).addOnSuccessListener {
            Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_LONG).show()
            dialog.hide()
        }
    }

    private fun loadSesion () {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_progress_bar_with_crear)
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun setupRecyclerView() {
        dbref = FirebaseDatabase.getInstance().getReference("users")
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtCC = findViewById<EditText>(R.id.txtCC)
        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtApellido = findViewById<EditText>(R.id.txtApellido)
        val txtCel = findViewById<EditText>(R.id.txtCel)
        val txtTypeAccount = findViewById<EditText>(R.id.txtTypeAccount)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                listUser.clear()

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(allUsers::class.java)
                        if (user != null) {
                            listUser.add(user)
                        }
                    }
                    for (i in listUser) {
                        if (i.key == saveKey) {

                            txtEmail.setText(i.email)
                            txtCC.setText(i.cc)
                            txtNombre.setText(i.name)
                            txtApellido.setText(i.lastname)
                            txtCel.setText(i.cel)
                            txtTypeAccount.setText(i.type)
                        }
                    }

                }

                if (txtEmail.text.toString() == "") {
                    txtEmail.isEnabled = true
                }

                if (txtCC.text.toString() == "") {
                    txtCC.isEnabled = true
                }

                if (txtNombre.text.toString() == "") {
                    txtNombre.isEnabled = true
                }

                if (txtApellido.text.toString() == "") {
                    txtApellido.isEnabled = true
                }

                if (txtCel.text.toString() == "") {
                    txtCel.isEnabled = true
                }

                if (txtTypeAccount.text.toString() == "") {
                    txtTypeAccount.isEnabled = true
                }

                when {
                    txtEmail.text.toString() == "" -> {
                        btnGuardar.text == "GUARDAR DATOS"
                    }
                    txtCC.text.toString() == "" -> {
                        btnGuardar.text == "GUARDAR DATOS"
                    }
                    txtNombre.text.toString() == "" -> {
                        btnGuardar.text == "GUARDAR DATOS"
                    }
                    txtApellido.text.toString() == "" -> {
                        btnGuardar.text == "GUARDAR DATOS"
                    }
                    txtCel.text.toString() == "" -> {
                        btnGuardar.text == "GUARDAR DATOS"
                    }
                    txtTypeAccount.text.toString() == "" -> {
                        btnGuardar.text == "GUARDAR DATOS"
                    }
                    else -> {
                        btnGuardar.text ="ACTUALIZAR DATOS"
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}