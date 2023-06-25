package com.example.swipe

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.swipe.databinding.TaskItemBinding

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {
    val taskList = ArrayList<Task>()

    inner class TaskHolder(task: View) : RecyclerView.ViewHolder(task) {
        val binding = TaskItemBinding.bind(task)
        var editText = binding.itemTaskText

        init {
            editText.addTextChangedListener { object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val test = taskList[adapterPosition]
                taskList[adapterPosition].text = p0.toString()

                   // db.getDao().updateitem(p0.toString(), taskList[adapterPosition].id)
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)

        return TaskHolder(view)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        var item = taskList[position]
        holder.bind(item)
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