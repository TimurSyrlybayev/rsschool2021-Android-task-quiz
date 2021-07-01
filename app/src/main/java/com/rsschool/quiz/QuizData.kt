package com.rsschool.quiz

data class QuizData(
    val questionsTitlesArray: Array<String>,
    val questionsArray: Array<String>,
    val answersArray: Array<Array<String>>,
    val chosenPointsArray: IntArray,
    val chosenAnswersArray: Array<String>,
    val correctAnswersArray: Array<String>,
    val themesArray: Array<Int>
)