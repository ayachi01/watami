package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class Activity_Settings : AppCompatActivity() {

    private lateinit var backtogame: ImageButton
    private lateinit var musicSeekBar: SeekBar
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var brownbar: View
    private lateinit var textView: TextView
    private lateinit var musictext: TextView
    private lateinit var ChangeTheme: Button
    private lateinit var darkmode: TextView
    private lateinit var screen: View
    private lateinit var toolbar: Toolbar


    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        this.backtogame = findViewById(R.id.backtogame)
        backtogame.setOnClickListener {
            val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)
        }
        //music
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        musicSeekBar = findViewById<SeekBar>(R.id.music)
        musicSeekBar.max = maxVolume

        musicSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        musicSeekBar.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)


        ChangeTheme = findViewById(R.id.ChangeTheme)
        sharedPreferences = getSharedPreferences("dark_mode_pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // Load the current dark mode state from SharedPreferences
        val isDarkModeEnabled = sharedPreferences.getBoolean("is_dark_mode_enabled", false)

        // Initialize views
        brownbar = findViewById(R.id.brownbar)
        textView = findViewById(R.id.textView)
        musictext = findViewById(R.id.musictext)
        darkmode = findViewById(R.id.darkmode)
        screen = findViewById(R.id.screen)
        toolbar = findViewById(R.id.toolbar)

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

    private fun updateDarkMode(isDarkModeEnabled: Boolean) {
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            // Update UI elements for dark mode
            screen.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_screen))
            brownbar.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_bar))
            textView.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_text))
            musictext.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_mdtext))
            darkmode.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_mdtext))
            ChangeTheme.setTextColor(ContextCompat.getColor(this, R.color.light_mode_buttons_text))
            ChangeTheme.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_buttons_settings))
            backtogame.setImageResource(R.drawable.backbutton_dark)
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_toolbar))


        } else {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            // Update UI elements for light mode
            screen.setBackgroundColor(ContextCompat.getColor(this, R.color.screen))
            brownbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_bar))
            textView.setTextColor(ContextCompat.getColor(this, R.color.light_mode_text))
            musictext.setTextColor(ContextCompat.getColor(this, R.color.light_mode_mdtext))
            ChangeTheme.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_buttons_settings))
            ChangeTheme.setTextColor(ContextCompat.getColor(this, R.color.light_mode_buttons_text))
            backtogame.setImageResource(R.drawable.backbutton )
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_toolbar))


        }
    }
}