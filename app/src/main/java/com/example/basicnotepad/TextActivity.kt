package com.example.basicnotepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private var text: Text? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        database = TextAppDatabase.getDatabase(this)
        textDao = database.textDao()

        CoroutineScope(Dispatchers.IO).launch {

            list = textDao.getNotes()

        }

        text = intent.getParcelableExtra("ACTUAL_TEXT")

        if(text != null) {

            binding.etText.setText(text!!.text)

        }

        val initialText = binding.etText.text.toString()

        binding.fabSave.visibility = View.INVISIBLE

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

                    val uid = textDao.getUid(text!!.date)
                    text!!.uid = uid
                    text!!.text = binding.etText.text.toString()
                    text!!.date = Calendar.getInstance().time.toString()
                    textDao.update(text!!)

                }

                withContext(Dispatchers.Main) {

                    setResult(RESULT_OK)
                    finish()

                }

            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.text_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menuDelete -> {

                    CoroutineScope(Dispatchers.IO).launch {

                        if(text == null) {

                            withContext(Dispatchers.Main) {

                                finish()

                            }

                        } else {

                            val uid = textDao.getUid(text!!.date)
                            text!!.uid = uid

                            textDao.delete(text!!)

                            withContext(Dispatchers.Main) {

                                setResult(RESULT_OK)
                                finish()
                                Toast.makeText(applicationContext, "Nota removida com sucesso", Toast.LENGTH_SHORT).show()

                            }

                        }

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

    override fun onBackPressed() {

        val dialog = TextDialogFragment()
        dialog.show(supportFragmentManager, "TextDialog")

    }

}