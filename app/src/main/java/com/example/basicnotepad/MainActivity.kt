package com.example.basicnotepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicnotepad.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: TextAppDatabase
    private lateinit var textDao: TextDao
    private lateinit var adapter: TextListAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var items: List<Text> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = TextAppDatabase.getDatabase(this)
        textDao = database.textDao()

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK) {

                CoroutineScope(Dispatchers.IO).launch {

                    items = textDao.getNotes()
                    adapter.setDataSet(items as ArrayList<Text>)

                    withContext(Dispatchers.Main) {

                        adapter.notifyDataSetChanged()

                    }

                }

            }
        }

        adapter = TextListAdapter {

            val intent = Intent(this, TextActivity::class.java)
            intent.putExtra("ACTUAL_TEXT", it)
            resultLauncher.launch(intent)

        }

        CoroutineScope(Dispatchers.IO).launch {

            items = textDao.getNotes()

            withContext(Dispatchers.Main) {

                adapter.setDataSet(items as ArrayList<Text>)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)

            }

        }

        binding.floatingActionButton.setOnClickListener {

            val intent = Intent(this, TextActivity::class.java)
            resultLauncher.launch(intent)

        }
    }

}