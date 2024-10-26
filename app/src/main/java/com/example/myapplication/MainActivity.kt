package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {

    private lateinit var start: Button
    private lateinit var settings: Button
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var screen: View
    private lateinit var howtoplay: Button
    private lateinit var dbHelper: DatabaseHelper // Declare dbHelper at the class level



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        this.settings = findViewById(R.id.settings)
        val soundPool = SoundPool.Builder().build()
        val soundId = soundPool.load(this, R.raw.mouse_click, 1)
        settings.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            val intent1 = Intent(this, Activity_Settings::class.java)
            startActivity(intent1)

        }

        this.start = findViewById(R.id.start)
        start.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            val intent2 = Intent(this, Activity_Game::class.java)
            startActivity(intent2)
        }

        this.howtoplay = findViewById(R.id.howtoplay)
        howtoplay.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            val intent3 = Intent(this, How_To_Play::class.java)
            startActivity(intent3)
        }

        val mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer.start()
        mediaPlayer.isLooping = true

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setOnAudioFocusChangeListener { focusChange ->
                when (focusChange) {
                    AudioManager.AUDIOFOCUS_LOSS -> mediaPlayer.pause()
                    AudioManager.AUDIOFOCUS_GAIN -> mediaPlayer.start()
                    // Handle other focus changes as needed
                }
            }
            .build()
        audioManager.requestAudioFocus(audioFocusRequest)

        sharedPreferences = getSharedPreferences("dark_mode_pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val isDarkModeEnabled = sharedPreferences.getBoolean("is_dark_mode_enabled", false)
        updateUI(isDarkModeEnabled)

        screen = findViewById(R.id.screen)

        if (isDarkModeEnabled) {
            updateDarkModeUI()
        } else {
            updateLightModeUI()
        }

    }


    private fun updateUI(isDarkModeEnabled: Boolean) {
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun updateDarkModeUI() {
        start.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_buttons))
        start.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_buttons_text))
        settings.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_buttons))
        settings.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_buttons_text))
        screen.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_screen))
        howtoplay.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_buttons))
        howtoplay.setTextColor(ContextCompat.getColor(this,R.color.dark_mode_buttons_text))
    }

    private fun updateLightModeUI() {
        start.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_buttons))
        start.setTextColor(ContextCompat.getColor(this, R.color.light_mode_buttons_text))
        settings.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_buttons))
        settings.setTextColor(ContextCompat.getColor(this, R.color.light_mode_buttons_text))
        screen.setBackgroundColor(ContextCompat.getColor(this, R.color.screen))
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer?.release()
    }
}