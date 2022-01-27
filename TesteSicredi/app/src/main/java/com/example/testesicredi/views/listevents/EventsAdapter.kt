package com.example.testesicredi.views.listevents

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.model.Event
import com.example.testesicredi.databinding.ItemEventListBinding

class EventsAdapter(
    private val context: Context,
    private val actions: EventActions
): ListAdapter<Event, EventsAdapter.EventHolder> (DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val inflater = LayoutInflater.from(context)
        val view = ItemEventListBinding.inflate(inflater, parent, false)
        return EventHolder(view, actions)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.binding.event = getItem(position)
    }

    inner class EventHolder(
        val binding: ItemEventListBinding,
        actions: EventActions
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.actions = actions
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem
    }
}