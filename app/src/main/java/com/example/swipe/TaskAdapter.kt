package com.example.swipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.swipe.databinding.TaskItemBinding

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {
    val taskList = ArrayList<Task>()

    class TaskHolder(task: View) : RecyclerView.ViewHolder(task) {
        val binding = TaskItemBinding.bind(task)

        fun bind(newTask : Task) = with(binding) {
            itemTaskText.text = newTask.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)

        return TaskHolder(view)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(taskList[position])
    }

    public fun ClearTaskItem()
    {
        taskList.clear()
        notifyDataSetChanged()
    }
    public fun AddTaskItem(task: Task) {
        taskList.add(task)
        notifyItemInserted(taskList.size - 1)
        //notifyDataSetChanged()
    }
}