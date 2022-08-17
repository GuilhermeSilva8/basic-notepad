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
    private lateinit var list: ArrayList<Text>
    private var pos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = TextAppDatabase.getDatabase(this)
        textDao = database.textDao()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /* fab initially invisible */
        //val text = intent.getStringExtra("ACTUAL_TEXT")
        list = intent.getParcelableArrayListExtra<Text>("LIST")!!
        //Log.d("MEULOG", list.toString())

        pos = intent.getIntExtra("POSITION", -1)

        if(pos != -1) {

            val text = list[pos].text
            binding.etText.setText(text)

        }

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


            CoroutineScope(Dispatchers.IO).launch {

                val data = Intent()
                //data.putExtra("TEXT", binding.etText.text.toString())
                if(initialText.isEmpty()) {

                    val newItem = Text(
                        binding.etText.text.toString(),
                        Calendar.getInstance().time.toString()
                    )
                    list.add(newItem)
                    textDao.insert(newItem)

                } else {

                    list[pos].text = binding.etText.text.toString()
                    list[pos].date = Calendar.getInstance().time.toString()
                    // atualiza a base

                }

                withContext(Dispatchers.Main) {

                    data.putParcelableArrayListExtra("NEW_LIST", list)
                    setResult(RESULT_OK, data)
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

                if(pos == -1) {

                    finish()

                } else {

                    list.removeAt(pos)
                    val data = Intent()
                    data.putParcelableArrayListExtra("NEW_LIST", list)
                    setResult(RESULT_OK, data)
                    finish()
                    Toast.makeText(this, "Nota removida com sucesso", Toast.LENGTH_SHORT).show()

                }

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