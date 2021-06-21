package com.rsschool.quiz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentContent6Binding

class ContentFragment6 : Fragment(R.layout.fragment_content6) {
    private var _binding: FragmentContent6Binding? = null
    private val binding get() = _binding!!
    private var array: IntArray? = IntArray(5)
    private var arrayWithAnswers: Array<String>? = arrayOf("", "", "", "", "")
    private val arrayWithCorrectAnswers: Array<String> = arrayOf(
        "Вторую",
        "Неожиданно, но... Мэри",
        "Лягушка",
        "Судья Рок",
        "Оранжевый верблюд",
    )
    private var sum = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContent6Binding.inflate(inflater, container, false)
        val view = binding.root
        arrayWithAnswers = arguments?.get("Chosen answers") as Array<String>
        array = arguments?.get("Checked points") as IntArray
        if (arrayWithAnswers != null) {
            for (i in arrayWithCorrectAnswers.indices) {
                if (arrayWithAnswers!![i] == arrayWithCorrectAnswers[i]) sum++
            }
        }
        binding.result.text = "Your result: ${sum * 20}%"

        val window = activity?.window
        window?.statusBarColor = Color.parseColor("#8A959C")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            val text = "Your result: ${sum * 20}%\n\n" +
                    "1) Вы участвуете в марафоне и обогнали бегуна, бежавшего вторым. " +
                    "Какую позицию вы теперь занимаете?\n" +
                    "Your answer: ${arrayWithAnswers!![0]}\n\n" +
                    "2) У отца Мэри есть пять дочерей. Четырех зовут: Чача, Чече, Чичи, Чочо." +
                    " Какое имя у пятой?\n" +
                    "Your answer: ${arrayWithAnswers!![1]}\n\n" +
                    "3) Что такое: зелёное, квакает, начинается на 'ля', заканчивается на 'гушка'?\n" +
                    "Your answer: ${arrayWithAnswers!![2]}\n\n" +
                    "4) Кто подставил кролика Роджера?\n" +
                    "Your answer: ${arrayWithAnswers!![3]}\n\n" +
                    "5) Продолжите песню: 'Оранжевое небо, Оранжевое море, Оранжевая зелень...'\n" +
                    "Your answer: ${arrayWithAnswers!![4]}\n\n"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Quiz results")
            intent.putExtra(Intent.EXTRA_TEXT, text)

            startActivity(Intent.createChooser(intent, "Please choose email service"))
        }

        fun exitFromApp() {
            val i: Intent? = activity?.let {
                activity?.packageManager
                    ?.getLaunchIntentForPackage(it.packageName)
            }
            if (i != null) {
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(i)
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

            exitFromApp()
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

            activity?.let { finishAffinity(it) }
        }

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitFromApp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}