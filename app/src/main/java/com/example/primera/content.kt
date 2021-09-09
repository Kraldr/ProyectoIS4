package com.example.primera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class content : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val meesage:String = intent.getStringExtra("Type").toString()

        Toast.makeText(applicationContext, meesage , Toast.LENGTH_LONG).show()
    }
}