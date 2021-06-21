package com.rsschool.quiz

import android.graphics.Color.parseColor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentContent5Binding

class ContentFragment5 : Fragment(R.layout.fragment_content5) {
    private var _binding: FragmentContent5Binding? = null
    private val binding get() = _binding!!
    private var array: IntArray? = IntArray(5)
    private var arrayWithAnswers: Array<String>? = arrayOf("", "", "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContent5Binding.inflate(inflater, container, false)
        val view = binding.root
        arrayWithAnswers = arguments?.get("Chosen answers") as Array<String>
        array = arguments?.get("Checked points") as IntArray
        if (array!![4] != 0) {
            for (radioButton in binding.radioGroup5) {
                if (radioButton.id == array!![4])
                    binding.radioGroup5.findViewById<RadioButton>(radioButton.id).isChecked = true
            }
        }
        val window = activity?.window
        window?.statusBarColor = parseColor("#5C001C")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()

        binding.nextButton5.isEnabled = binding.radioGroup5.checkedRadioButtonId != -1

        binding.radioGroup5.setOnCheckedChangeListener { _, _ ->
            binding.nextButton5.isEnabled = true
        }

        binding.nextButton5.setOnClickListener {
            if (binding.radioGroup5.checkedRadioButtonId != -1) {
                array?.set(4, binding.radioGroup5.checkedRadioButtonId)
                arrayWithAnswers?.set(
                    4,
                    binding.radioGroup5
                        .findViewById<RadioButton>(binding.radioGroup5.checkedRadioButtonId)
                        .text.toString()
                )
                bundle.putIntArray("Checked points", array)
                bundle.putStringArray("Chosen answers", arrayWithAnswers)

                val fragment6 = ContentFragment6()
                val fragmentTransaction = parentFragmentManager.beginTransaction()
                fragment6.arguments = bundle

                fragmentTransaction.replace(R.id.fragment_container_view, fragment6)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        fun returnBack() {
            val fragment4 = ContentFragment4()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            if (binding.radioGroup5.checkedRadioButtonId != -1) {
                array?.set(4, binding.radioGroup5.checkedRadioButtonId)
                arrayWithAnswers?.set(
                    4,
                    binding.radioGroup5
                        .findViewById<RadioButton>(binding.radioGroup5.checkedRadioButtonId)
                        .text.toString()
                )
            }
            bundle.putIntArray("Checked points", array)
            bundle.putStringArray("Chosen answers", arrayWithAnswers)
            fragment4.arguments = bundle
            fragmentTransaction.replace(R.id.fragment_container_view, fragment4)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        binding.previousButton5.setOnClickListener {
            returnBack()
        }

        binding.toolbar5.setNavigationOnClickListener {
            returnBack()
        }

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returnBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}