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
import com.rsschool.quiz.databinding.FragmentContent3Binding

class ContentFragment3 : Fragment(R.layout.fragment_content3) {
    private var _binding: FragmentContent3Binding? = null
    private val binding get() = _binding!!
    private var array: IntArray? = IntArray(5)
    private var arrayWithAnswers: Array<String>? = arrayOf("", "", "", "", "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContent3Binding.inflate(inflater, container, false)
        val view = binding.root
        arrayWithAnswers = arguments?.get("Chosen answers") as Array<String>
        array = arguments?.get("Checked points") as IntArray
        if (array!![2] != 0) {
            for (radioButton in binding.radioGroup3) {
                if (radioButton.id == array!![2])
                    binding.radioGroup3.findViewById<RadioButton>(radioButton.id).isChecked = true
            }
        }
        val window = activity?.window
        window?.statusBarColor = Color.parseColor("#963000")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()

        binding.nextButton3.isEnabled = binding.radioGroup3.checkedRadioButtonId != -1

        binding.radioGroup3.setOnCheckedChangeListener { _, _ ->
            binding.nextButton3.isEnabled = true
        }

        binding.nextButton3.setOnClickListener {
            if (binding.radioGroup3.checkedRadioButtonId != -1) {
                array?.set(2, binding.radioGroup3.checkedRadioButtonId)
                arrayWithAnswers?.set(
                    2,
                    binding.radioGroup3
                        .findViewById<RadioButton>(binding.radioGroup3.checkedRadioButtonId)
                        .text.toString()
                )
                bundle.putIntArray("Checked points", array)
                bundle.putStringArray("Chosen answers", arrayWithAnswers)

                val fragment4 = ContentFragment4()
                val fragmentTransaction = parentFragmentManager.beginTransaction()
                fragment4.arguments = bundle

                fragmentTransaction.replace(R.id.fragment_container_view, fragment4)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        fun returnBack() {
            val fragment2 = ContentFragment2()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            if (binding.radioGroup3.checkedRadioButtonId != -1) {
                array?.set(2, binding.radioGroup3.checkedRadioButtonId)
                arrayWithAnswers?.set(
                    2,
                    binding.radioGroup3
                        .findViewById<RadioButton>(binding.radioGroup3.checkedRadioButtonId)
                        .text.toString()
                )
            }
            bundle.putIntArray("Checked points", array)
            bundle.putStringArray("Chosen answers", arrayWithAnswers)
            fragment2.arguments = bundle
            fragmentTransaction.replace(R.id.fragment_container_view, fragment2)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        binding.previousButton3.setOnClickListener {
            returnBack()
        }

        binding.toolbar3.setNavigationOnClickListener {
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