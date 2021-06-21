package com.rsschool.quiz

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentContent4Binding

class ContentFragment4 : Fragment(R.layout.fragment_content4) {
    private var _binding: FragmentContent4Binding? = null
    private val binding get() = _binding!!
    private var array: IntArray? = IntArray(5)
    private var arrayWithAnswers: Array<String>? = arrayOf("", "", "", "", "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContent4Binding.inflate(inflater, container, false)
        val view = binding.root
        arrayWithAnswers = arguments?.get("Chosen answers") as Array<String>
        array = arguments?.get("Checked points") as IntArray
        if (array!![3] != 0) {
            for (radioButton in binding.radioGroup4) {
                if (radioButton.id == array!![3])
                    binding.radioGroup4.findViewById<RadioButton>(radioButton.id).isChecked = true
            }
        }
        val window = activity?.window
        window?.statusBarColor = Color.parseColor("#006B0C")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()

        binding.nextButton4.isEnabled = binding.radioGroup4.checkedRadioButtonId != -1

        binding.radioGroup4.setOnCheckedChangeListener { _, _ ->
            binding.nextButton4.isEnabled = true
        }

        binding.nextButton4.setOnClickListener {
            if (binding.radioGroup4.checkedRadioButtonId != -1) {
                array?.set(3, binding.radioGroup4.checkedRadioButtonId)
                arrayWithAnswers?.set(
                    3,
                    binding.radioGroup4
                        .findViewById<RadioButton>(binding.radioGroup4.checkedRadioButtonId)
                        .text.toString()
                )
                bundle.putIntArray("Checked points", array)
                bundle.putStringArray("Chosen answers", arrayWithAnswers)

                val fragment5 = ContentFragment5()
                val fragmentTransaction = parentFragmentManager.beginTransaction()
                fragment5.arguments = bundle

                fragmentTransaction.replace(R.id.fragment_container_view, fragment5)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        fun returnBack() {
            val fragment3 = ContentFragment3()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            if (binding.radioGroup4.checkedRadioButtonId != -1) {
                array?.set(3, binding.radioGroup4.checkedRadioButtonId)
                arrayWithAnswers?.set(
                    3,
                    binding.radioGroup4
                        .findViewById<RadioButton>(binding.radioGroup4.checkedRadioButtonId)
                        .text.toString()
                )
            }
            bundle.putIntArray("Checked points", array)
            bundle.putStringArray("Chosen answers", arrayWithAnswers)
            fragment3.arguments = bundle
            fragmentTransaction.replace(R.id.fragment_container_view, fragment3)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        binding.previousButton4.setOnClickListener {
            returnBack()
        }

        binding.toolbar4.setNavigationOnClickListener {
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