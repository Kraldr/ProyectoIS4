package com.example.primera

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.primera.databinding.ActivityContentReproBinding
import com.google.android.exoplayer2.ui.PlayerView
import android.widget.RelativeLayout

import android.content.pm.ActivityInfo

import android.R
import android.view.ViewGroup

import androidx.core.content.ContextCompat




class contentRepro : AppCompatActivity() {

    private var meesage:String = ""
    private lateinit var binding: ActivityContentReproBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContentReproBinding.inflate(layoutInflater)
        setContentView(binding.root)

        meesage = intent.getStringExtra("url").toString()


        binding.exoPlayerView.prepare(Uri.parse(meesage))


    }

    override fun onPause() {
        super.onPause()
        binding.exoPlayerView.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.exoPlayerView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.exoPlayerView.onDestroy()
    }
}