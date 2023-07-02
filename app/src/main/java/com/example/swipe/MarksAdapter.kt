package com.example.swipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.swipe.databinding.NotificationItemBinding

val USER_COMPORATOR = object : DiffUtil.ItemCallback<ItemList>() {
    override fun areItemsTheSame(oldItem: ItemList, newItem: ItemList): Boolean {
        return oldItem.itemListId == newItem.itemListId
    }

    override fun areContentsTheSame(oldItem: ItemList, newItem: ItemList): Boolean {
        return oldItem.item == newItem.item && oldItem.userCreatorId == newItem.userCreatorId
    }

}

class MarksAdapter: PagingDataAdapter<ItemList, MarksAdapter.PagingHolder>(USER_COMPORATOR) {

    inner class PagingHolder(view : View) : RecyclerView.ViewHolder(view) {
        val binding = NotificationItemBinding.bind(view)
    }


    override fun onBindViewHolder(holder: PagingHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_marks, parent, false)
        return PagingHolder(view)
    }
}