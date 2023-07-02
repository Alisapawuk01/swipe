package com.example.swipe

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.swipe.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var insertedIndex = 0L
    private val taskListAdapter = TaskListAdapter()
    private lateinit var picker : MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()
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
        val name: CharSequence = "channel"
        val description = "Channel For Alarm Manager"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel( "pawuk", name, importance)
        channel.description = description
        channel.enableVibration(true)
        val notificationManager = getSystemService(
            NotificationManager::class.java)

        notificationManager.createNotificationChannel(channel)

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

                override fun onTimeClick(index : Long) {
                    calendar = Calendar.getInstance()
                    calendar.timeInMillis = System.currentTimeMillis()
                    picker = MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(calendar[Calendar.HOUR_OF_DAY])
                        .setMinute(calendar[Calendar.MINUTE])
                        .setTitleText("Установка времени")
                        .build()

                    picker.show(supportFragmentManager, "pawuk")
                    picker.addOnPositiveButtonClickListener {

                        calendar[Calendar.HOUR_OF_DAY] = picker.hour
                        calendar[Calendar.MINUTE] = picker.minute
                        calendar[Calendar.SECOND] = 0
                        calendar[Calendar.MILLISECOND] = 0

//                        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_YEAR), picker.hour, picker.minute, 0)

                        alarmManager = this@MainActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                        val intent = Intent (this@MainActivity, Notifications::class.java)
                        intent.putExtra("BtnClickIndex", index)
                        pendingIntent = PendingIntent.getBroadcast(
                            this@MainActivity,
                            index.toInt(),
                            intent,
                            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                        )

                        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_DAY, pendingIntent)
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis , pendingIntent)

                    }
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