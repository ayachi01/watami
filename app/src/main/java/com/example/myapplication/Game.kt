package com.example.myapplication

data class Game(
    val riddle_id: Int,
    val riddle_question: String,
    val riddle_choices: List<String>,
    val riddle_answer: String,
    val riddle_points: Int,
    val riddle_difficulty: Int,
)