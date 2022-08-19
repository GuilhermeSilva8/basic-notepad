package com.example.basicnotepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.basicnotepad.databinding.ActivityTextBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTextBinding
    private lateinit var database: TextAppDatabase
    private lateinit var textDao: TextDao
    private lateinit var list: List<Text>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = TextAppDatabase.getDatabase(this)
        textDao = database.textDao()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val text = intent.getParcelableExtra<Text>("ACTUAL_TEXT")

        CoroutineScope(Dispatchers.IO).launch {

            list = textDao.getNotes()

        }

        if(text != null) {

            binding.etText.setText(text.text)

        }

        binding.fabSave.visibility = View.INVISIBLE

        val initialText = binding.etText.text.toString()

        binding.etText.doAfterTextChanged {

            if(binding.etText.text.toString() != initialText) {
                binding.fabSave.visibility = View.VISIBLE
            } else {
                binding.fabSave.visibility = View.INVISIBLE
            }

        }

        binding.fabSave.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                if(text == null) {

                    val newItem = Text(
                        binding.etText.text.toString(),
                        Calendar.getInstance().time.toString()
                    )
                    textDao.insert(newItem)

                } else {

                    val uid = textDao.getUid(text.date)
                    text.uid = uid
                    text.text = binding.etText.text.toString()
                    text.date = Calendar.getInstance().time.toString()
                    textDao.update(text)

                }

                withContext(Dispatchers.Main) {

                    setResult(RESULT_OK)
                    finish()

                }

            }

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
                /*
                if(pos == -1) {

                    finish()

                } else {

                    list.removeAt(pos)
                    val data = Intent()
                    data.putParcelableArrayListExtra("NEW_LIST", list)
                    setResult(RESULT_OK, data)
                    finish()
                    Toast.makeText(this, "Nota removida com sucesso", Toast.LENGTH_SHORT).show()

                }*/

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

    /* shows a custom dialog when we press the back button */
    override fun onBackPressed() {

        val dialog = TextDialogFragment()
        dialog.show(supportFragmentManager, "TextDialog")

    }

}