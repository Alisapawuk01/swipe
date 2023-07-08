package com.example.swipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MarksActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private val list = mutableListOf<String>()
    private val actualList = mutableListOf<String>()
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marks)
        val db = MainDb.getDb(this)
        val btnIndex = intent.getLongExtra("BtnClickIndex", 0)

        lifecycleScope.launch {
            val items = db.getDao().getListPage(btnIndex)
            items.forEach {
                list.add(it.item)
            }
        }

        actualList.add(list.first())
        index += 1

        listView = findViewById(R.id.listView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, actualList)
        listView.adapter = adapter
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        val animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        val controller = LayoutAnimationController(animation)
        listView.layoutAnimation = controller
        listView.startLayoutAnimation()

        listView.setOnItemClickListener { parent, view, position, id ->
            if (index >= list.size) {
                return@setOnItemClickListener
            }
            listView.clearChoices()
            adapter.clear()
            adapter.add(list.get(index))
            index += 1
            val selectedTask = list[position]
            Toast.makeText(applicationContext, "Selected task: $selectedTask", Toast.LENGTH_SHORT).show()

            // Дополнительные действия при выборе пункта в списке
        }
    }

}