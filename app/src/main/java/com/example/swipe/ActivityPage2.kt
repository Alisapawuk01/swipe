package com.example.swipe

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.swipe.databinding.ActivityPage2Binding
import kotlin.math.max

class ActivityPage2 : AppCompatActivity() {
    lateinit var taskBinding : ActivityPage2Binding
    private val taskAdapter = TaskAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskBinding = ActivityPage2Binding.inflate(layoutInflater)
        setContentView(taskBinding.root)
        init()
    }
    public  var globalIndexList = 1L
    private var listIndex = 0L
    private fun init() {
        val db = MainDb.getDb(this)

        val btnIndex = intent.getLongExtra("BtnClickIndex", 0)

        val items = db.getDao().getAllItemsById(btnIndex)

        for(item in items)
        {
            for (subItem in item.textlist)
            {
                val task = Task(subItem.item, 0)
                taskAdapter.AddTaskItem(task)
            }
        }

        taskBinding.apply {
            recViewP2.layoutManager = GridLayoutManager(this@ActivityPage2, 2)
            recViewP2.adapter = taskAdapter
            AddTaskBtn.setOnClickListener {
                val task = Task(editText.text.toString(), 0)
                taskAdapter.AddTaskItem(task)
                listIndex += 1

                Thread {
                    val itemtext = ItemList(userCreatorId = btnIndex, item = task.text)
                    db.getDao().insertReminder(itemtext)
                }.start()
                editText.text.clear()
            }

        }
    }
}