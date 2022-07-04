package com.example.basicnotepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basicnotepad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val text = intent.getStringExtra("TEXT")
        binding.textView.text = text

        binding.floatingActionButton.setOnClickListener {

            val intent = Intent(this, TextActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}