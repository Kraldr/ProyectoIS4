package com.example.primera

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.primera.databinding.ActivityRegistrarseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.String
import java.util.*
import java.util.regex.Pattern

class Registrarse : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegistrarseBinding
    private lateinit var type: kotlin.String
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        supportActionBar?.hide();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.WHITE


        val spinner = binding.spinner
        val list = resources.getStringArray(R.array.type)
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


        binding.btnRegistro.setOnClickListener {

            val mEmail = binding.txtEmail.text.toString()
            val name: kotlin.String = binding.txtNombre.text.toString()
            val lastname: kotlin.String = binding.txtApellido.text.toString()
            val mPassword = binding.txtPass.text.toString()
            val mRepeatPassword = binding.txtRepeatPass.text.toString()
            val mType = type

            val passwordRegex = Pattern.compile("^" +
                    "(?=.*[-@#$%^&+/=])" +     // Al menos 1 carácter especial
                    ".{6,}" +                // Al menos 6 caracteres
                    "$")

            if(mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                Toast.makeText(baseContext, "Ingrese un email valido.",
                    Toast.LENGTH_SHORT).show()
            } else if (mPassword != mRepeatPassword) {
                Toast.makeText(baseContext, "Confirma la contraseña.",
                    Toast.LENGTH_SHORT).show()
            }else if (type == "Seleccione una opción") {
                Toast.makeText(baseContext, "Seleccione el tipo de cuenta",
                    Toast.LENGTH_SHORT).show()
            } else {
                loadSesion()
                createAccount(mEmail, mPassword, name, lastname, mType)
            }

        }

    }

    private fun loadSesion () {

        dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_progress_bar_with_register)
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun createAccount(email : kotlin.String, password : kotlin.String, name:kotlin.String,lastname:kotlin.String, mType: kotlin.String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val database = FirebaseDatabase.getInstance().getReference("users")
                    var uniqueID = UUID.randomUUID().toString()
                    val users = allUsers(uniqueID, email, "" ,name,lastname, "" ,mType)
                    database.child(uniqueID).setValue(users).addOnSuccessListener {}
                    val intent = Intent(this, menuList::class.java)
                    saveData(correo = email, online = true, mType, uniqueID)
                    Toast.makeText(baseContext, "Registrado correctamente",
                        Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    dialog.hide()
                    finish()
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    dialog.hide()
                }
            }
    }

    private fun saveData (correo: kotlin.String, online:Boolean, regisType: kotlin.String, key: kotlin.String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("correo", correo)
            putString("type", regisType)
            putBoolean("online", online)
            putString("key", key)
        }.apply()
    }

    private fun loadData (): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val saveEmail: kotlin.String = sharedPreferences.getString("correo", null).toString()
        val saveOnline: Boolean = sharedPreferences.getBoolean("online", false)
        return (saveOnline)
    }
}