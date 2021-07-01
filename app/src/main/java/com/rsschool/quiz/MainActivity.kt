package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ManagingInterface {
    private val questionsTitles = arrayOf("Вопрос 1", "Вопрос 2", "Вопрос 3", "Вопрос 4", "Вопрос 5")
    private val questions = arrayOf(
        "Вы участвуете в марафоне и обогнали бегуна, бежавшего вторым. Какую позицию вы теперь занимаете?",
        "У отца Мэри есть пять дочерей. Четырех зовут: Чача, Чече, Чичи, Чочо. Какое имя у пятой?",
        "Что такое: зелёное, квакает, начинается на 'ля', заканчивается на 'гушка'?",
        "Кто подставил кролика Роджера?",
        "Продолжите песню: 'Оранжевое небо, Оранжевое море, Оранжевая зелень...'"
    )
    private val answersForFirstQuestion = arrayOf(
        "Первую",
        "Вторую",
        "Третью",
        "Четвертую",
        "Такое невозможно"
    )
    private val answersForSecondQuestion = arrayOf(
        "Чуча",
        "Чё? Чё?",
        "Мария-Антуанетта",
        "Её не зовут, она сама приходит",
        "Неожиданно, но... Мэри"
    )
    private val answersForThirdQuestion = arrayOf(
        "Крокодил",
        "Бегемот",
        "Что-то есть захотелось...",
        "Лягушка",
        "Шифоньер"
    )
    private val answersForFourthQuestion = arrayOf(
        "Малыш Герман",
        "Судья Рок",
        "Джессика Рэббит",
        "Эдди Валиант",
        "Кролик Роджер подставил сам себя"
    )
    private val answersForFifthQuestion = arrayOf(
        "Оранжевый верблюд",
        "Оранжевый орангутанг",
        "Оранжевый апельсин",
        "Заводной апельсин",
        "Заманчиво поют"
    )
    private val chosenPoints = IntArray(questions.size)
    private val chosenAnswers = Array(questions.size) { "" }
    private val correctAnswers = arrayOf(
        "Вторую",
        "Неожиданно, но... Мэри",
        "Лягушка",
        "Судья Рок",
        "Оранжевый верблюд"
    )
    private val themesArray = arrayOf(
        R.style.Theme_Quiz_First,
        R.style.Theme_Quiz_Second,
        R.style.Theme_Quiz_Third,
        R.style.Theme_Quiz_Fourth,
        R.style.Theme_Quiz_Fifth,
        R.style.Theme_Quiz_Sixth
    )
    private val data = QuizData(
        questionsTitles,
        questions,
        arrayOf(
            answersForFirstQuestion,
            answersForSecondQuestion,
            answersForThirdQuestion,
            answersForFourthQuestion,
            answersForFifthQuestion
        ),
        chosenPoints,
        chosenAnswers,
        correctAnswers,
        themesArray
    )
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_view, FragmentQuiz(
                    data.questionsTitlesArray,
                    data.questionsArray,
                    data.answersArray,
                    data.chosenPointsArray,
                    data.chosenAnswersArray,
                    count,
                    themesArray
                ), null)
                .commit()
        }

    }

    override fun goToPreviousFragment() {
        if (findViewById<RadioGroup>(R.id.radio_group).checkedRadioButtonId != -1) {
            val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
            chosenPoints[count] = radioGroup.checkedRadioButtonId
            chosenAnswers[count] = radioGroup
                .findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                .text.toString()
        }
        count--
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentQuiz(
            data.questionsTitlesArray,
            data.questionsArray,
            data.answersArray,
            data.chosenPointsArray,
            data.chosenAnswersArray,
            count,
            themesArray
        )
        with(transaction) {
            replace(R.id.fragment_container_view, fragment)
            addToBackStack(null)
        }
        transaction.commit()
    }

    override fun goToNextFragment() {
        if (findViewById<RadioGroup>(R.id.radio_group).checkedRadioButtonId != -1) {
            val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
            chosenPoints[count] = radioGroup.checkedRadioButtonId
            chosenAnswers[count] = radioGroup
                .findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                .text.toString()
            count++
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = FragmentQuiz(
                data.questionsTitlesArray,
                data.questionsArray,
                data.answersArray,
                data.chosenPointsArray,
                data.chosenAnswersArray,
                count,
                themesArray
            )
            with(transaction) {
                replace(R.id.fragment_container_view, fragment)
                addToBackStack(null)
            }
            transaction.commit()
        }
    }

    override fun goToResultFragment() {
        if (findViewById<RadioGroup>(R.id.radio_group).checkedRadioButtonId != -1) {
            val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
            chosenPoints[count] = radioGroup.checkedRadioButtonId
            chosenAnswers[count] = radioGroup
                .findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                .text.toString()
            count++
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = FragmentResult(
                data.questionsTitlesArray,
                data.questionsArray,
                data.answersArray,
                data.chosenPointsArray,
                data.chosenAnswersArray,
                count,
                correctAnswers,
                themesArray
            )
            with(transaction) {
                replace(R.id.fragment_container_view, fragment)
                addToBackStack(null)
            }
            transaction.commit()
        }
    }
}