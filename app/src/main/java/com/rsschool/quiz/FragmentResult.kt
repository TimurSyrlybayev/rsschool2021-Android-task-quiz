package com.rsschool.quiz

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding
import kotlin.system.exitProcess

class FragmentResult(
    private val questionsTitlesArray: Array<String>,
    private val questionsArray: Array<String>,
    private val answersArray: Array<Array<String>>,
    private val chosenPointsArray: IntArray,
    private val chosenAnswersArray: Array<String>,
    private val questionNumber: Int,
    private val correctAnswersArray: Array<String>,
    private val themesArray: Array<Int>
) : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private var sum = 0
    private var result = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contextThemeWrapper = ContextThemeWrapper(activity, themesArray.last())
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        val typedValue = TypedValue()
        val currentTheme = contextThemeWrapper.theme
        currentTheme?.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)

        val window = activity?.window
        window?.statusBarColor = typedValue.data
        _binding = FragmentResultBinding.inflate(localInflater, container, false)
        if (chosenAnswersArray != null) {
            for (i in correctAnswersArray.indices) {
                if (chosenAnswersArray[i] == correctAnswersArray[i]) sum++
            }
        }
        result = sum * (100 / questionsArray.size)
        binding.result.text = "Ваш результат: $result%"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog = AlertDialog.Builder(activity)

        binding.share.setOnClickListener {
            val timer = object : CountDownTimer(300, 100) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.share.setPadding(20, 20, 20, 20)
                }

                override fun onFinish() {
                    binding.share.setPadding(0, 0, 0, 0)
                }
            }
            timer.start()

            val text = "Ваш результат: $result%\n\n" +
                    "1) ${questionsArray[0]}\n" +
                    "Ваш ответ: ${chosenAnswersArray[0]}\n\n" +
                    "Правильный ответ: ${correctAnswersArray[0]}\n\n" +
                    "2) ${questionsArray[1]}\n" +
                    "Ваш ответ: ${chosenAnswersArray[1]}\n\n" +
                    "Правильный ответ: ${correctAnswersArray[1]}\n\n" +
                    "3) ${questionsArray[2]}\n" +
                    "Ваш ответ: ${chosenAnswersArray[2]}\n\n" +
                    "Правильный ответ: ${correctAnswersArray[2]}\n\n" +
                    "4) ${questionsArray[3]}\n" +
                    "Ваш ответ: ${chosenAnswersArray[3]}\n\n" +
                    "Правильный ответ: ${correctAnswersArray[3]}\n\n" +
                    "5) ${questionsArray[4]}\n" +
                    "Ваш ответ: ${chosenAnswersArray[4]}\n\n" +
                    "Правильный ответ: ${correctAnswersArray[4]}\n\n"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Quiz results")
            intent.putExtra(Intent.EXTRA_TEXT, text)

            startActivity(Intent.createChooser(intent, "Please choose email service"))
        }

        fun restartApp() {
            dialog.setTitle("Вы действительно хотите начать заново?")
            dialog.setPositiveButton("Да") { _, _ ->
                val i: Intent? = activity?.let {
                    activity?.packageManager
                        ?.getLaunchIntentForPackage(it.packageName)
                }
                if (i != null) {
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
                startActivity(i)
            }
            dialog.setNegativeButton("Нет") { _, _ -> }
            dialog.show()
        }

        binding.back.setOnClickListener {
            val timer = object : CountDownTimer(300, 100) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.back.setPadding(20, 20, 20, 20)
                }

                override fun onFinish() {
                    binding.back.setPadding(0, 0, 0, 0)
                }
            }
            timer.start()
            restartApp()
        }

        binding.close.setOnClickListener { _ ->
            val timer = object : CountDownTimer(300, 100) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.close.setPadding(20, 20, 20, 20)
                }

                override fun onFinish() {
                    binding.close.setPadding(0, 0, 0, 0)
                }
            }
            timer.start()


            dialog.setTitle("Вы действительно хотите выйти?")
            dialog.setPositiveButton("Да") { _, _ ->
                exitProcess(0)
            }
            dialog.setNegativeButton("Нет") { _,_ -> }
            dialog.show()
        }

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                restartApp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}