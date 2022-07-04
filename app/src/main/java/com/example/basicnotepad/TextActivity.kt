package com.example.basicnotepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.basicnotepad.databinding.ActivityTextBinding

class TextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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

        /*  when clicking on the fab we go to the MainActivity and send the text by the intent */
        binding.fabSave.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("TEXT", binding.etText.text.toString())
            startActivity(intent)
            finish()

        }

    }

    /* menu */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.text_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menuDelete -> {
                Toast.makeText(this, "Remover elemento", Toast.LENGTH_SHORT).show()

                true
            }
            else -> {
                false
            }
        }
    }

    /* putting a visible back button with the same action as the phone back button */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}