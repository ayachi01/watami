package com.example.myapplication

data class Riddle(
    val riddle_id: Int,
    val riddle_question: String,
    val riddle_answer: String,
    val riddle_choices: String,
    val riddle_points: Int,
    val riddle_difficulty: Int
)
