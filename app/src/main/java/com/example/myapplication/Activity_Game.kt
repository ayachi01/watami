package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.Toolbar
import android.widget.Toast
import retrofit2.*

class Activity_Game : AppCompatActivity() {

    private lateinit var settings2: ImageButton
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var screen: View
    private lateinit var brownbar: View
    private lateinit var submitanswer: Button
    private lateinit var ResetButton: ImageButton
    private lateinit var tv_question: TextView
    private lateinit var tv_optionOne: TextView
    private lateinit var tv_optionTwo: TextView
    private lateinit var tv_optionThree: TextView
    private lateinit var ScoreOutput: TextView
    private lateinit var Score: TextView
    private lateinit var toolbar: Toolbar
    private var currentQuestionIndex = 0
    private var score = 0
    private var questions: MutableList<Game> = mutableListOf()// Assuming Game contains question and options
    private lateinit var databaseHelper: DatabaseHelper
    private var mSelectedOptionPosition = 0
    private var mCurrentPosition = 1
    private var difficulty: String = "easy" // Example difficulty, set according to your logic

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val points = intent.getIntExtra("points", 0)

        // Initialize views
        tv_question = findViewById(R.id.tv_question)
        tv_optionOne = findViewById(R.id.tv_optionOne)
        tv_optionTwo = findViewById(R.id.tv_optionTwo)
        tv_optionThree = findViewById(R.id.tv_optionThree)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        submitanswer = findViewById(R.id.submitanswer)

        // Initialize other components (sound, shared preferences, etc.)
        // Initialize sound pool
        val soundPool = SoundPool.Builder().build()
        val soundId = soundPool.load(this, R.raw.mouse_click, 1)

        fetchRiddles()

        settings2 = findViewById(R.id.settings2)
        settings2.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            val intent2 = Intent(this, Activity_Game_to_Settings::class.java)
            startActivity(intent2)
        }

        ResetButton = findViewById(R.id.ResetButton)
        ResetButton.setOnClickListener {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            val intent = Intent(this, Activity_Game::class.java)
            startActivity(intent)
            finish()
        }

        // Initialize SharedPreferences for dark mode
        sharedPreferences = getSharedPreferences("dark_mode_pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val isDarkModeEnabled = sharedPreferences.getBoolean("is_dark_mode_enabled", false)
        updateUI(isDarkModeEnabled)

        // Initialize UI components
        screen = findViewById(R.id.screen)
        brownbar = findViewById(R.id.brownbar)
        submitanswer = findViewById(R.id.submitanswer)
        ScoreOutput = findViewById(R.id.ScoreOutput)
        Score = findViewById(R.id.Score)

        ScoreOutput.text = points.toString()

        // Dark mode UI setup
        if (isDarkModeEnabled) {
            updateDarkModeUI()
        } else {
            updateLightModeUI()
        }
    }

    private fun getRandomQuestion(): Game? {
        return if (questions.isNotEmpty()) {
            val randomIndex = (questions.indices).random()
            questions[randomIndex]
        } else {
            null
        }
    }

    private fun fetchRiddles() {
        // Make the network call directly with Retrofit's enqueue
        DatabaseHelper.apiService.getRiddles().enqueue(object: Callback<List<Game>> {
            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.isSuccessful) {
                    questions.clear()
                    questions.addAll(response.body()!!)

                    displayRandomQuestion()
                } else {
                    Log.e("Riddles", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                Log.e("Riddles", "Failure: ${t.message}")
            }
        })
    }

    private fun displayRandomQuestion() {
        val randomQuestion = getRandomQuestion()
        val choiceViews = listOf(tv_optionOne, tv_optionTwo, tv_optionThree)

        randomQuestion?.let {
            tv_question.text = it.riddle_question
            val choices = it.riddle_choices
            val answer = it.riddle_answer
            val points = it.riddle_points
            val difficulty = it.riddle_difficulty
            var currentPoints = ScoreOutput.text.toString().toInt()

            // Update UI with choices, assuming you have 3 choices
            if (choices.size >= 3) {
                tv_optionOne.text = choices[0]
                tv_optionTwo.text = choices[1]
                tv_optionThree.text = choices[2]
            }

            for (choice in choiceViews) {
                choice.setOnClickListener {
                    if (choice.text == answer) {
                        currentPoints += points
                        Toast.makeText(this, "Correct answer!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Activity_Game::class.java)
                        intent.putExtra("points", currentPoints)
                        startActivity(intent)
                        finish()
                    } else {
                        currentPoints -= points

                        if (currentPoints <= 0) {
                            currentPoints = 0
                            Toast.makeText(this, "Game over!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("points", currentPoints)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Incorrect answer!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, Activity_Game::class.java)
                            intent.putExtra("points", currentPoints)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        } ?: run {
            tv_question.text = "No questions available."
            tv_optionOne.text = ""
            tv_optionTwo.text = ""
            tv_optionThree.text = ""
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
        screen.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_screen))
        brownbar.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_bar))
        submitanswer.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_mode_buttons))
        submitanswer.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_buttons_text))
        Score.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_text))
        ScoreOutput.setTextColor(ContextCompat.getColor(this, R.color.dark_mode_text))
        settings2.setImageResource(R.drawable.dark_mode_settingsbutton)
        ResetButton.setImageResource(R.drawable.dark_mode_resetbutton)
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
        ResetButton.setImageResource(R.drawable.resetbutton)
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_mode_bar))
    }
}
