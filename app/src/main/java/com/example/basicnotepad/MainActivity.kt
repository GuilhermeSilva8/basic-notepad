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
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TextListAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var items: ArrayList<Text> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* result launcher which receives the text data from text activity and create a new item for the rv with the actual date */
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK) {
                result.data?.apply {
                    /*val newItem = Text(
                        getStringExtra("TEXT")!!,
                        Calendar.getInstance().time.toString()
                    )
                    items.add(newItem)*/

                    items = getParcelableArrayListExtra("NEW_LIST")!!
                    //Log.d("MEULOG", items.toString())
                    adapter.setDataSet(items)
                    adapter.notifyDataSetChanged()

                }
            }
        }

        /* adapter and recyclerview setups */
        adapter = TextListAdapter {

            val intent = Intent(this, TextActivity::class.java)
            //val text = it.text
            //intent.putExtra("ACTUAL_TEXT", text)
            intent.putParcelableArrayListExtra("LIST", items)
            intent.putExtra("POSITION", items.indexOf(it))
            resultLauncher.launch(intent)

        }

        adapter.setDataSet(items)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        /* fab that executes the result launcher */
        binding.floatingActionButton.setOnClickListener {

            val intent = Intent(this, TextActivity::class.java)
            intent.putParcelableArrayListExtra("LIST", items)
            resultLauncher.launch(intent)

        }
    }
}