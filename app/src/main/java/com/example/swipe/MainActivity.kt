package com.example.swipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.swipe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = MainDb.getDb(this)
        binding.button.setOnClickListener {
            val item = Item(null,
                binding.edtext.text.toString()
            )
            Thread {
                db.getDao().InsertItem(item)

            }.start()

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