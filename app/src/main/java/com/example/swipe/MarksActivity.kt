package com.example.swipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MarksActivity : AppCompatActivity() {
    private val todoItems = arrayOf("Task 1", "Task 2", "Task 3", "Task 4") // пункты списка дел
    private lateinit var listView: ListView
    private val pageConfig = PagingConfig(pageSize = 1, prefetchDistance = 10, enablePlaceholders = false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marks)
        val db = MainDb.getDb(this)
        val btnIndex = intent.getLongExtra("BtnClickIndex", 0)
        val pager = Pager(config = pageConfig, pagingSourceFactory = {PageSource(db.getDao(), btnIndex)})
        val list = mutableListOf<String>()
        lifecycleScope.launch {

        }
        listView = findViewById(R.id.listView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, todoItems)
        listView.adapter = adapter
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedTask = todoItems[position]
            Toast.makeText(applicationContext, "Selected task: $selectedTask", Toast.LENGTH_SHORT).show()

            // Дополнительные действия при выборе пункта в списке
        }
    }

}