package com.rsschool.quiz

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.view.iterator
import androidx.fragment.app.*
import com.rsschool.quiz.databinding.FragmentContent1Binding

class ContentFragment1 : Fragment(R.layout.fragment_content1) {
    private var _binding: FragmentContent1Binding? = null
    private val binding get() = _binding!!
    private var array: IntArray? = IntArray(5)
    private var arrayWithAnswers: Array<String> = arrayOf("", "", "", "", "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContent1Binding.inflate(inflater, container, false)

        if (arguments?.get("Checked points") != null) {
            array = arguments?.get("Checked points") as IntArray
            if (array!![0] != 0) {
                for (radioButton in binding.radioGroup1) {
                    if (radioButton.id == array!![0]) binding.radioGroup1
                        .findViewById<RadioButton>(radioButton.id).isChecked = true
                }
            }
        }
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.let { finishAffinity(it) }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        val window = activity?.window
        window?.statusBarColor = Color.parseColor("#FF300298")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        binding.nextButton1.isEnabled = binding.radioGroup1.checkedRadioButtonId != -1

        binding.radioGroup1.setOnCheckedChangeListener { _, _ ->
            binding.nextButton1.isEnabled = true
        }

        binding.nextButton1.setOnClickListener {
            if (binding.radioGroup1.checkedRadioButtonId != -1) {
                array?.set(0, binding.radioGroup1.checkedRadioButtonId)
                arrayWithAnswers[0] = binding.radioGroup1
                    .findViewById<RadioButton>(binding.radioGroup1.checkedRadioButtonId)
                    .text.toString()
                bundle.putIntArray("Checked points", array)
                bundle.putStringArray("Chosen answers", arrayWithAnswers)

                val fragment2 = ContentFragment2()
                val fragmentTransaction = parentFragmentManager.beginTransaction()
                fragment2.arguments = bundle

                fragmentTransaction.replace(R.id.fragment_container_view, fragment2)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}