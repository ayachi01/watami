package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class Activity_Game_to_Settings : AppCompatActivity() {

    private lateinit var backtogame: ImageButton
    private lateinit var ChangeTheme: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var brownbar: View
    private lateinit var textView: TextView
    private lateinit var musictext: TextView
    private lateinit var darkmode: TextView
    private lateinit var screen: View
    private lateinit var exit: ImageButton
    private lateinit var toolbar: Toolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_to_settings)

        val soundPool = SoundPool.Builder().build()
        val soundId = soundPool.load(this, R.raw.mouse_click, 1)

        this.backtogame = findViewById(R.id.backtogame)
        backtogame.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            val intent2 = Intent(this, Activity_Game::class.java)
            startActivity(intent2)
        }

        this.exit = findViewById(R.id.exit)
        exit.setOnClickListener{
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            val intent3 = Intent(this, MainActivity::class.java)
            startActivity(intent3)
        }

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val music = findViewById<SeekBar>(R.id.music)
        music.max = maxVolume

        music.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    var startPoint = seekBar.progress
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    var endPoint = seekBar.progress
                }
            }
        })
        music.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        this.ChangeTheme = findViewById(R.id.ChangeTheme)
        sharedPreferences = getSharedPreferences("dark_mode_pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // Load the current dark mode state from SharedPreferences
        val isDarkModeEnabled = sharedPreferences.getBoolean("is_dark_mode_enabled", false)

        // Initialize views
        textView = findViewById(R.id.textView)
        brownbar = findViewById(R.id.brownbar)
        musictext = findViewById(R.id.musictext)
        darkmode = findViewById(R.id.darkmode)
        screen = findViewById(R.id.screen)
        toolbar = findViewById<Toolbar>(R.id.toolbar)

        // Set the dark mode button listener
        ChangeTheme.setOnClickListener {
            toggleDarkMode()
        }
        // Update UI elements based on the current dark mode state
        updateDarkMode(isDarkModeEnabled)
    }

    private fun toggleDarkMode() {
        val isDarkModeEnabled = sharedPreferences.getBoolean("is_dark_mode_enabled", false)
        val newIsDarkModeEnabled = !isDarkModeEnabled
        editor.putBoolean("is_dark_mode_enabled", newIsDarkModeEnabled)
        editor.apply()
        updateDarkMode(newIsDarkModeEnabled)
    }

    @SuppressLint("ResourceType")
    private fun updateDarkMode(isDarkModeEnabled: Boolean) {
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            // Update UI elements for dark mode
            screen.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_screen))
            brownbar.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_bar))
            musictext.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_mdtext))
            darkmode.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_mdtext))
            ChangeTheme.setTextColor(ContextCompat.getColor(this, R.color.light_mode_buttons_text))
            ChangeTheme.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_buttons_settings))
            backtogame.setImageResource(R.drawable.backbutton_dark)
            textView.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_text))
            exit.setImageResource(R.drawable.dark_mode_exit)
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_toolbar))

        } else {
            // Update UI elements for light mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            screen.setBackgroundColor(ContextCompat.getColor(this, R.color.screen))
            brownbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_bar))
            musictext.setTextColor(ContextCompat.getColor(this, R.color.light_mode_mdtext))
            darkmode.setTextColor(ContextCompat.getColor(this, R.color.light_mode_mdtext))
            ChangeTheme.setTextColor(ContextCompat.getColor(this, R.color.light_mode_buttons_text))
            ChangeTheme.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_buttons_settings))
            backtogame.setImageResource(R.drawable.backbutton)
            textView.setTextColor(ContextCompat.getColor(this, R.color.light_mode_text))
            exit.setImageResource(R.drawable.exitbutton)
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_toolbar))
        }
    }
}

