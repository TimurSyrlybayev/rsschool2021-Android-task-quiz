package com.rsschool.quiz

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding
import kotlin.system.exitProcess

class FragmentQuiz(
    private val questionsTitlesArray: Array<String>,
    private val questionsArray: Array<String>,
    private val answersArray: Array<Array<String>>,
    private val chosenPointsArray: IntArray,
    private val chosenAnswersArray: Array<String>,
    private val questionNumber: Int,
    private val themesArray: Array<Int>
) : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (questionNumber < themesArray.size) {
            val contextThemeWrapper = ContextThemeWrapper(activity, themesArray[questionNumber])
            val localInflater = inflater.cloneInContext(contextThemeWrapper)
            val typedValue = TypedValue()
            val currentTheme = contextThemeWrapper.theme
            currentTheme?.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)

            val window = activity?.window
            window?.statusBarColor = typedValue.data
            _binding = FragmentQuizBinding.inflate(localInflater, container, false)
        } else {
            val contextThemeWrapper = ContextThemeWrapper(activity, themesArray[questionNumber % themesArray.size])
            val localInflater = inflater.cloneInContext(contextThemeWrapper)
            val typedValue = TypedValue()
            val currentTheme = contextThemeWrapper.theme
            currentTheme?.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)

            val window = activity?.window
            window?.statusBarColor = typedValue.data
            _binding = FragmentQuizBinding.inflate(localInflater, container, false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog = AlertDialog.Builder(activity)

        if (chosenPointsArray[questionNumber] != 0) {
            val radioGroup = view?.findViewById<RadioGroup>(R.id.radio_group)
            if (radioGroup != null) {
                for (radioButton in radioGroup) {
                    if (radioButton.id == chosenPointsArray[questionNumber])
                        radioGroup.findViewById<RadioButton>(radioButton.id).isChecked = true
                }
            }
        }

        with(binding) {
            toolbar.title = questionsTitlesArray[questionNumber]
            question.text = questionsArray[questionNumber]
            for (index in answersArray.indices) {
                val radioButton = radioGroup.getChildAt(index) as RadioButton
                radioButton.text = answersArray[questionNumber][index]
            }
        }
        val previousButton = binding.previousButton
        val nextButton = binding.nextButton
        val toolbar = binding.toolbar
        val listener = activity as ManagingInterface

        when(questionNumber) {
            0 -> {
                previousButton.isEnabled = false
                binding.toolbar.navigationIcon = null
            }
            (questionsArray.size - 1) -> nextButton.text = "Итог"
        }

        fun returnBack() {
            if (questionNumber > 0) listener.goToPreviousFragment()
        }

        previousButton.setOnClickListener {
            returnBack()
        }

        toolbar.setNavigationOnClickListener {
            returnBack()
        }

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when (questionNumber) {
                    0 -> {
                        dialog.setTitle("Вы действительно хотите выйти?")
                        dialog.setPositiveButton("Да") { _, _ ->
                            exitProcess(0)
                        }
                        dialog.setNegativeButton("Нет") { _,_ -> }
                        dialog.show()
                    }
                    else -> returnBack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        nextButton.setOnClickListener {
            if (questionNumber < questionsArray.size - 1) {
                listener.goToNextFragment()
            } else if (questionNumber == questionsArray.size - 1) {
                listener.goToResultFragment()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}