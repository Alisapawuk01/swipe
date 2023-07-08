package com.example.swipe

import android.content.Intent
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.swipe.databinding.TaskItemBinding

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {
    val taskList = ArrayList<Task>()
    val CAMERA_REQUEST_CODE = 0

    inner class TaskHolder(task: View) : RecyclerView.ViewHolder(task) {
        private val binding = TaskItemBinding.bind(task)
        var editText = binding.itemTaskText

        init {
            editText.addTextChangedListener { object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                taskList[adapterPosition].text = p0.toString()

            }

        } }

        }

        fun bind(newTask : Task) = with(binding) {
            itemTaskText.setText(newTask.text)
            val db = MainDb.getDb(itemTaskText.context)
            deleteBt.setOnClickListener{
                Toast.makeText(itemTaskText.context, newTask.id.toString(), Toast.LENGTH_SHORT).show()
                Thread {
                 db.getDao().deleteItemById(newTask.id)
                }.start()
            }
            cameraBtn.setOnClickListener {
                val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (callCameraIntent.resolveActivity(itemTaskText.context.packageManager) != null) {
                    startActivity(itemTaskText.context, callCameraIntent, null)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)

        return TaskHolder(view)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val taskItem = taskList[position]
        holder.bind(taskItem)
        holder.editText.doAfterTextChanged {
            val db = MainDb.getDb(holder.editText.context)
            val item = db.getDao().getTodoItemById(taskList[position].id)
            item.item = it.toString()
            db.getDao().updateItem2(item)
        }
    }

    public fun ClearTaskItem()
    {
        taskList.clear()
        notifyDataSetChanged()
    }
    public fun AddTaskItem(task: Task) {
        taskList.add(task)
        //notifyItemInserted(taskList.size - 1)
        notifyDataSetChanged()
    }
}