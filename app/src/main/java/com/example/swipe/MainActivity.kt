package com.example.swipe

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.swipe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var insertedIndex = 0L
    private val taskListAdapter = TaskListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        val db = MainDb.getDb(this)
//        db.getDao().getAllItem().asLiveData().observe(this) {list->
//            binding.textView.text = ""
//            list.forEach {
//                val text = "Id: ${it.todoList.todoListId} Name: ${it.todoList.name}\n"
//                binding.textView.append(text)
//                it.textlist.forEach{ it1 ->
//                    val text2 = "Id: ${it1.itemListId} id2: ${it1.userCreatorId} Name: ${it1.item}\n"
//                    binding.textView.append(text2)
//                }
//           }
//        }

        createNotificationChannel()

        db.getDao().getAllItem().asLiveData().observe(this) { list->
            taskListAdapter.ClearTaskList()
            list.forEach {
                val taskData = TaskList(it.todoList.todoListId)
                taskListAdapter.AddTaskList(taskData)
            }
        }

        binding.button.setOnClickListener {
            val item = TodoList( name=
                "ListNameIdx${insertedIndex}"
            )
//
//            Thread {
//                val id = db.getDao().insertItem(item)
//                val itemtext = ItemList(userCreatorId =  id, item = "test1")
//                val itemtext2 = ItemList(userCreatorId =  id, item =  "test2")
//                db.getDao().insertReminder(itemtext, itemtext2)
//            }.start()
            Thread {
                insertedIndex = db.getDao().insertItem(item)
            }.start()
            val intent = Intent(".ActivityPage2")
            intent.putExtra("BtnClickIndex", insertedIndex + 1)
            startActivity(intent)
           }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name: CharSequence = "pawuk channel"
            val description = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel( "pawuk", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java)

            notificationManager.createNotificationChannel(channel)

    }
    }

    private fun init()
    {
        binding.apply {
            taskListRV.layoutManager = GridLayoutManager(this@MainActivity, 3)
            taskListRV.adapter = taskListAdapter
            taskListAdapter.activity = supportFragmentManager
            taskListAdapter.SetOnClickListener(object :
                TaskListAdapter.OnClickListener {
                override fun onClick(index : Long) {
                    val intent = Intent(".ActivityPage2")
                    intent.putExtra("BtnClickIndex", index)
                    startActivity(intent)
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MyLogMAct", "onStart" )
    }
    override fun onResume() {
        super.onResume()
        Log.d("MyLogMAct", "onResume" )
    }

    override fun onPause() {
        super.onPause()
        Log.d("MyLogMAct", "onPause" )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyLogMAct", "onDestroy" )
    }

    override fun onStop() {
        super.onStop()
        Log.d("MyLogMAct", "onStop" )
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MyLogMAct", "onRestart" )
    }

}