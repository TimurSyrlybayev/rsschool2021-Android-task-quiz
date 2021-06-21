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
import com.rsschool.quiz.databinding.FragmentContent2Binding

class ContentFragment2 : Fragment(R.layout.fragment_content2) {
    private var _binding: FragmentContent2Binding? = null
    private val binding get() = _binding!!
    private var array: IntArray? = IntArray(5)
    private var arrayWithAnswers: Array<String>? = arrayOf("", "", "", "", "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContent2Binding.inflate(inflater, container, false)
        val view = binding.root
        arrayWithAnswers = arguments?.get("Chosen answers") as Array<String>
        array = arguments?.get("Checked points") as IntArray
        if (array!![1] != 0) {
            for (radioButton in binding.radioGroup2) {
                if (radioButton.id == array!![1])
                    binding.radioGroup2.findViewById<RadioButton>(radioButton.id).isChecked = true
            }
        }
        val window = activity?.window
        window?.statusBarColor = Color.parseColor("#FF007BFF")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()

        binding.nextButton2.isEnabled = binding.radioGroup2.checkedRadioButtonId != -1

        binding.radioGroup2.setOnCheckedChangeListener { _, _ ->
            binding.nextButton2.isEnabled = true
        }

        binding.nextButton2.setOnClickListener {
            if (binding.radioGroup2.checkedRadioButtonId != -1) {
                array?.set(1, binding.radioGroup2.checkedRadioButtonId)
                arrayWithAnswers?.set(
                    1,
                    binding.radioGroup2
                        .findViewById<RadioButton>(binding.radioGroup2.checkedRadioButtonId)
                        .text.toString()
                )
                bundle.putIntArray("Checked points", array)
                bundle.putStringArray("Chosen answers", arrayWithAnswers)

                val fragment3 = ContentFragment3()
                val fragmentTransaction = parentFragmentManager.beginTransaction()
                fragment3.arguments = bundle

                fragmentTransaction.replace(R.id.fragment_container_view, fragment3)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        fun returnBack() {
            val fragment1 = ContentFragment1()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            if (binding.radioGroup2.checkedRadioButtonId != -1) {
                array?.set(1, binding.radioGroup2.checkedRadioButtonId)
                arrayWithAnswers?.set(
                    1,
                    binding.radioGroup2
                        .findViewById<RadioButton>(binding.radioGroup2.checkedRadioButtonId)
                        .text.toString()
                )
            }
            bundle.putIntArray("Checked points", array)
            bundle.putStringArray("Chosen answers", arrayWithAnswers)
            fragment1.arguments = bundle
            fragmentTransaction.replace(R.id.fragment_container_view, fragment1)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        binding.previousButton2.setOnClickListener {
            returnBack()
        }

        binding.toolbar2.setNavigationOnClickListener {
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