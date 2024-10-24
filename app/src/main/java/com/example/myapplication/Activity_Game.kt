package com.example.myapplication

import com.example.myapplication.Game
import com.example.myapplication.DatabaseHelper
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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.Toolbar
import android.widget.Toast

class Activity_Game : AppCompatActivity(){

    private lateinit var settings2: ImageButton
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var screen: View
    private lateinit var brownbar: View
    private lateinit var submitanswer: Button
    private lateinit var resetbutton: ImageButton
    private lateinit var tv_question: TextView
    private lateinit var choice1: Button
    private lateinit var choice2: Button
    private lateinit var choice3: Button
    private lateinit var ScoreOutput: TextView
    private lateinit var Score: TextView
    private lateinit var toolbar: Toolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val soundPool = SoundPool.Builder().build()
        val soundId = soundPool.load(this, R.raw.mouse_click, 1)

        settings2 = findViewById(R.id.settings2)
        settings2.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            val intent2 = Intent(this, Activity_Game_to_Settings::class.java)
            startActivity(intent2)
        }

        resetbutton = findViewById(R.id.resetbutton)
        resetbutton.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            // resetGame()
        }

        tv_question = findViewById(R.id.tv_question)
        choice1 = findViewById(R.id.choice1)
        choice2 = findViewById(R.id.choice2)
        choice3 = findViewById(R.id.choice3)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        submitanswer = findViewById(R.id.submitanswer)
        screen = findViewById(R.id.screen)
        brownbar = findViewById(R.id.brownbar)
        submitanswer = findViewById(R.id.submitanswer)
        ScoreOutput = findViewById(R.id.ScoreOutput)
        Score = findViewById(R.id.Score)


        // Initialize SharedPreferences for dark mode
        sharedPreferences = getSharedPreferences("dark_mode_pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val isDarkModeEnabled = sharedPreferences.getBoolean("is_dark_mode_enabled", false)
        updateUI(isDarkModeEnabled)




        // Dark mode UI setup
        if (isDarkModeEnabled) {
            updateDarkModeUI()
        } else {
            updateLightModeUI()
        }
    }

    //override fun onClick(v: View?) {
        //when (v?.id) {
            //R.id.choice1 -> {
                //selectedOptionView(choice1, 1)
            //}
            //R.id.choice2 -> {
               // selectedOptionView(choice2, 2)
            //}
            //R.id.choice3 -> {
              //  selectedOptionView(choice3, 3)
            //}
            //R.id.submitanswer -> {
              //  if (mSelectedOptionPosition != 0) {
                //    mCurrentPosition++

                    // Scoring
                  //  if (isAnswerCorrect(mSelectedOptionPosition)) {
                    //    addPoints(1)
                   // } else {
                     //   deductPoints(1)
                    //}
                //} else {
                    // Handle the case when no option is selected
                  //  Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
                //}
            //}
        //}
    //}




   // private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        // Highlight selected option
        // Update mSelectedOptionPosition
     //   mSelectedOptionPosition = selectedOptionNum
        // Reset other options if needed
    //}

    //private fun isAnswerCorrect(selectedOption: Int): Boolean {
        // Implement logic to check if the selected option is correct
     //   return true // Placeholder
    //}

    //private fun addPoints(points: Int) {
      //  score += points
        //ScoreOutput.text = score.toString() // Update score display
    //}

    //private fun deductPoints(points: Int) {
      //  score -= points
      //  ScoreOutput.text = score.toString() // Update score display
    //}


    private fun updateUI(isDarkModeEnabled: Boolean) {
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun updateDarkModeUI() {
        screen.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_screen))
        brownbar.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_bar))
        submitanswer.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_buttons))
        submitanswer.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_buttons_text))
        Score.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_text))
        ScoreOutput.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_text))
        settings2.setImageResource(R.drawable.dark_mode_settingsbutton)
        resetbutton.setImageResource(R.drawable.dark_mode_resetbutton)
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_toolbar))
    }

    private fun updateLightModeUI() {
        screen.setBackgroundColor(ContextCompat.getColor(this, R.color.screen))
        brownbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_bar))
        submitanswer.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_submit))
        submitanswer.setTextColor(ContextCompat.getColor(this, R.color.light_mode_submit_text))
        Score.setTextColor(ContextCompat.getColor(this, R.color.light_mode_score))
        ScoreOutput.setTextColor(ContextCompat.getColor(this, R.color.light_mode_score))
        settings2.setImageResource(R.drawable.settingsbutton)
        resetbutton.setImageResource(R.drawable.resetbutton)
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_toolbar))
    }
}
