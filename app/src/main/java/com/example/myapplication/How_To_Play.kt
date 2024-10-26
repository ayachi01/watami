package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class How_To_Play : AppCompatActivity() {

    private lateinit var backtogame: ImageButton
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var screen: View
    private lateinit var textView: TextView
    private lateinit var brownbar: View
    private lateinit var instructions: TextView
    private lateinit var scoringbase: TextView
    private lateinit var toolbar: Toolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_how_to_play)


        val soundPool = SoundPool.Builder().build()
        val soundId = soundPool.load(this, R.raw.mouse_click, 1)

        this.backtogame = findViewById(R.id.backtogame)
        backtogame.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            val intent1 = Intent(this, MainActivity::class.java)
            startActivity(intent1)

        }
        sharedPreferences = getSharedPreferences("dark_mode_pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val isDarkModeEnabled = sharedPreferences.getBoolean("is_dark_mode_enabled", false)
        updateUI(isDarkModeEnabled)

        this.screen = findViewById(R.id.screen)
        this.textView = findViewById(R.id.textView)
        this.brownbar = findViewById(R.id.brownbar)
        this.instructions = findViewById(R.id.instructions)
        this.scoringbase = findViewById(R.id.scoringbase)
        toolbar = findViewById<Toolbar>(R.id.toolbar)

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
        screen.setBackgroundColor(ContextCompat.getColor(this,R.color.dark_screen))
        textView.setTextColor(ContextCompat.getColor(this,R.color.dark_mode_text))
        brownbar.setBackgroundColor(ContextCompat.getColor(this,R.color.dark_mode_bar))
        backtogame.setImageResource(R.drawable.backbutton_dark)
        instructions.setTextColor(ContextCompat.getColor(this,R.color.dark_mode_mdtext))
        scoringbase.setTextColor(ContextCompat.getColor(this,R.color.dark_mode_mdtext))
        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.dark_mode_bar))

    }
    private fun updateLightModeUI() {
        screen.setBackgroundColor(ContextCompat.getColor(this,R.color.screen))
        textView.setTextColor(ContextCompat.getColor(this,R.color.light_mode_text))
        brownbar.setBackgroundColor(ContextCompat.getColor(this,R.color.light_mode_bar))
        backtogame.setImageResource(R.drawable.backbutton)
        instructions.setTextColor(ContextCompat.getColor(this,R.color.light_mode_mdtext))
        scoringbase.setTextColor(ContextCompat.getColor(this,R.color.light_mode_mdtext))
    }
}