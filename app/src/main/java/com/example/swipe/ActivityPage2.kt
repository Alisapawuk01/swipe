package com.example.swipe

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.swipe.databinding.ActivityPage2Binding
import kotlin.math.max

class ActivityPage2 : AppCompatActivity() {
    lateinit var taskBinding : ActivityPage2Binding
    private val taskAdapter = TaskAdapter()
    private var listIndex = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskBinding = ActivityPage2Binding.inflate(layoutInflater)
        setContentView(taskBinding.root)
        init()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        taskBinding = ActivityPage2Binding.inflate(layoutInflater)
        setContentView(taskBinding.root)
        init()
    }
    private fun init() {
        val db = MainDb.getDb(this)

        val btnIndex = intent.getLongExtra("BtnClickIndex", 0)

        val items = db.getDao().getAllItemsById(btnIndex)

        for(item in items)
        {
            for (subItem in item.textlist)
            {
                val task = Task(subItem.item, 0, subItem.itemListId)
                taskAdapter.AddTaskItem(task)
            }
        }

        taskBinding.apply {
            recViewP2.layoutManager = GridLayoutManager(this@ActivityPage2, 2)
            recViewP2.adapter = taskAdapter
            AddTaskBtn.setOnClickListener {
                val taskText = editText.text.toString()
                val itemtext = ItemList(userCreatorId = btnIndex, item = taskText)

                listIndex = db.getDao().insertReminder(itemtext)

                val task = Task(taskText, 0, listIndex )
                taskAdapter.AddTaskItem(task)
                editText.text.clear()
            }

        }
    }
}