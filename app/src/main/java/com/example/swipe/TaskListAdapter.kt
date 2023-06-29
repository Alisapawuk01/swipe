package com.example.swipe

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swipe.databinding.TaskItemBinding
import com.example.swipe.databinding.TaskListBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskListHolder>() {
    val tasks = ArrayList<TaskList>()
    private var onClickListener: OnClickListener? = null
     private lateinit var picker : MaterialTimePicker
   public var activity: FragmentManager? = null
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    class TaskListHolder(view: View) : RecyclerView.ViewHolder(view)  {
        val binding =  TaskListBinding.bind(view)

        fun bind(taskList : TaskList) = with(binding) {
            taskListBtn.text = taskList.taskIndex.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list, parent, false)

        return TaskListHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskListHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item)
        holder.binding.taskListBtn.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(item.taskIndex )
            }
        }


        holder.binding.deleteBtn.setOnClickListener {
            DeleteItem(position, holder.binding.deleteBtn.context)
        }

        holder.binding.timeBt.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onTimeClick(item.taskIndex )
            }
        }
    }

    public fun ClearTaskList()
    {
        tasks.clear()
        notifyDataSetChanged()
    }

    public fun DeleteItem(position: Int, context: Context)
    {
        val item = tasks[position]
        val db = MainDb.getDb(context)

        Thread {
            db.getDao().deleteListId(item.taskIndex)
        }.start()

        tasks.removeAt(position)

        notifyDataSetChanged()
    }

    public fun SetOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    public fun AddTaskList(taskList: TaskList) {

        tasks.add(taskList)
        notifyItemInserted(tasks.size - 1)

        //notifyDataSetChanged()
    }
    interface OnClickListener {
        fun onClick(index : Long)
        fun onTimeClick(index : Long)
    }
}