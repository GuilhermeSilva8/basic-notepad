package com.example.basicnotepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basicnotepad.databinding.ActivityTextBinding

class TextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}