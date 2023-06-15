package com.example.swipe

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.asLiveData
import com.example.swipe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var insertedIndex = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = MainDb.getDb(this)
        db.getDao().getAllItem().asLiveData().observe(this) {list->
            binding.textView.text = ""
            list.forEach {
                val text = "Id: ${it.todoList.todoListId} Name: ${it.todoList.name}\n"
                binding.textView.append(text)
                it.textlist.forEach{ it1 ->
                    val text2 = "Id: ${it1.itemListId} id2: ${it1.userCreatorId} Name: ${it1.item}\n"
                    binding.textView.append(text2)
                }
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
            intent.putExtra("globalIndexList", insertedIndex)
            startActivity(intent)
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