package com.example.hw3.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hw3.databinding.HeaderUsersItemBinding
import com.example.hw3.databinding.LeftItemBinding
import com.example.hw3.databinding.RightItemBinding
import com.example.hw3.model.ItemType

class ChatAdapter : androidx.recyclerview.widget.ListAdapter<ItemType, RecyclerView.ViewHolder>(diff) {
    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is ItemType.FirstUser -> 0
            is ItemType.SecondUser -> 1
            is ItemType.HeaderUserList -> 2
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> LeftMessageHolder(LeftItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            1 -> RightMessageHolder(RightItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> UsersHolder(HeaderUsersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LeftMessageHolder -> holder.bind((currentList[position]) as ItemType.FirstUser)
            is RightMessageHolder -> holder.bind((currentList[position]) as ItemType.SecondUser)
            is UsersHolder -> holder.bind()
        }
    }

    inner class LeftMessageHolder(private val binding: LeftItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: ItemType.FirstUser) {
            val text = userData.message.userId.toString() + ": " + userData.message.message
            binding.leftMessageTextView.text = text
        }
    }

    inner class RightMessageHolder(private val binding: RightItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: ItemType.SecondUser) {
            val text = userData.message.userId.toString() + ": " + userData.message.message
            binding.rightMessageTextView.text = text
        }
    }

    inner class UsersHolder(private val binding: HeaderUsersItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    companion object diff : DiffUtil.ItemCallback<ItemType>() {
        override fun areItemsTheSame(oldItem: ItemType, newItem: ItemType): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemType, newItem: ItemType): Boolean {
            return when (oldItem) {
                is ItemType.FirstUser -> oldItem.message == (newItem as ItemType.FirstUser).message
                is ItemType.SecondUser -> oldItem.message == (newItem as ItemType.SecondUser).message
                is ItemType.HeaderUserList ->
                    (oldItem.counterFirstUser == (newItem as ItemType.HeaderUserList).counterFirstUser
                            && oldItem.counterSecondUser == newItem.counterSecondUser)
            }
        }
    }
}