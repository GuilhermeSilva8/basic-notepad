package com.example.basicnotepad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.basicnotepad.databinding.FragmentTextDialogBinding

class TextDialogFragment: DialogFragment() {

    private lateinit var binding: FragmentTextDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTextDialogBinding.inflate(inflater, container, false)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnClose.setOnClickListener {

            requireActivity().finish()
            dismiss()

        }

        return binding.root

    }

}