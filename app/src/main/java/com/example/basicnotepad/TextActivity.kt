package com.example.basicnotepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.example.basicnotepad.databinding.ActivityTextBinding

class TextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* fab initially invisible */
        binding.fabSave.visibility = View.INVISIBLE

        val initialText = binding.etText.text.toString()

        /* if the text is different from the initial text, the fab is visible */
        binding.etText.doAfterTextChanged {

            if(binding.etText.text.toString() != initialText) {
                binding.fabSave.visibility = View.VISIBLE
            } else {
                binding.fabSave.visibility = View.INVISIBLE
            }

        }


    }
}