package com.example.primera

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.Z
import android.view.WindowManager
import android.widget.Toast
import com.example.primera.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var type: String = ""
    private val database = Firebase.database
    private lateinit var dbref : DatabaseReference
    private lateinit var binding: ActivityMainBinding
    private lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        supportActionBar?.hide();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.WHITE

        binding.btnRegistroNuevo.setOnClickListener {
            cargarRegistro()
        }


        if (loadData()) {
            val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
            val saveEmail: String = sharedPreferences.getString("correo", null).toString()
            startExist(saveEmail)
        }



        binding.btnIngresar.setOnClickListener {
            if (isNetworkAvailbale()) {
                val mEmail = binding.txtCorreo.text.toString()
                val mPassword = binding.txtContrasena.text.toString()

                when {
                    mEmail.isEmpty() || mPassword.isEmpty() -> {
                        Toast.makeText(baseContext, "Correo o contraseña incorrectos.",
                            Toast.LENGTH_SHORT).show()
                    } else -> {
                    loadSesion()
                    SignIn(mEmail, mPassword)
                }
                }
            }else {
                Toast.makeText(baseContext, "Por favor verifique la conexión a internet",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView(saveEmail: String) {

        dbref = FirebaseDatabase.getInstance().getReference("users")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){

                        val user = userSnapshot.getValue(allUsers::class.java)
                        if (user != null) {
                            if (user.email == saveEmail) {
                                type = user.type
                                val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.apply {
                                    putString("type", type)
                                    putString("key", user.key)
                                }.apply()
                            }
                        }
                    }



                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }

    fun  isNetworkAvailbale():Boolean{
        val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo =conManager.activeNetworkInfo
        return internetInfo!=null && internetInfo.isConnected
    }

    private fun loadSesion () {

        dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_progress_bar_with_text)
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun SignIn (email : String , password : String) {
        setupRecyclerView(email)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    val intent = Intent(this, menuList::class.java).apply {

                    }
                    Toast.makeText(this, "Sesión iniciada", Toast.LENGTH_LONG).show()
                    saveData (email, true, type)
                    startActivity(intent)
                    dialog.hide()
                    finish()
                } else {
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Correo o contraseña icorrectos.",
                        Toast.LENGTH_SHORT).show()
                    dialog.hide()
                }
            }
    }

    private fun cargarRegistro() {
        val intent = Intent(this, Registrarse::class.java).apply {

        }
        startActivity(intent)
    }


    private fun startExist (correo: String) {
        val intent = Intent(this, menuList::class.java).apply {

        }
        //Toast.makeText(this, "Sesión iniciada", Toast.LENGTH_LONG).show()
        startActivity(intent)
        finish()
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

    private fun loadData (): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val saveEmail: String = sharedPreferences.getString("correo", null).toString()
        val saveOnline: Boolean = sharedPreferences.getBoolean("online", false)
        return (saveOnline)
    }
}