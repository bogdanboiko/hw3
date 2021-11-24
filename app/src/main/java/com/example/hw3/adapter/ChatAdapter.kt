package com.example.hw3.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hw3.databinding.HeaderUsersItemBinding
import com.example.hw3.databinding.LeftItemBinding
import com.example.hw3.databinding.RightItemBinding
import com.example.hw3.model.ItemType

interface UserChangeClickListener {
    var currentUser: Int

    fun userClick(user: Int, binding: HeaderUsersItemBinding)

    fun itemLongPress(item: ItemType, user: Int)
}

class ChatAdapter(
    val userListener: UserChangeClickListener
    ) : androidx.recyclerview.widget.ListAdapter<ItemType, RecyclerView.ViewHolder>(diff) {
    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is ItemType.FirstUser -> VIEW_TYPE_FIRST_USER
            is ItemType.SecondUser -> VIEW_TYPE_SECOND_USER
            is ItemType.HeaderUserList -> VIEW_TYPE_HEADER_USERS
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_FIRST_USER ->
                LeftMessageHolder(LeftItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            VIEW_TYPE_SECOND_USER ->
                RightMessageHolder(RightItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else ->
                UsersHolder(HeaderUsersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LeftMessageHolder -> holder.bind((currentList[position]) as ItemType.FirstUser)
            is RightMessageHolder -> holder.bind((currentList[position]) as ItemType.SecondUser)
            is UsersHolder -> holder.bind(currentList[position] as ItemType.HeaderUserList)
        }
    }

    inner class LeftMessageHolder(private val binding: LeftItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: ItemType.FirstUser) {
            val text = "User 1: " + userData.message.message
            binding.leftMessageTextView.text = text
            binding.leftMessageTextView.setOnLongClickListener {
                userListener.itemLongPress(userData, VIEW_TYPE_FIRST_USER)
                return@setOnLongClickListener true
            }
        }
    }

    inner class RightMessageHolder(private val binding: RightItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: ItemType.SecondUser) {
            val text = "User 2: " + userData.message.message
            binding.rightMessageTextView.text = text
            binding.rightMessageTextView.setOnLongClickListener {
                userListener.itemLongPress(userData, VIEW_TYPE_SECOND_USER)
                return@setOnLongClickListener true
            }
        }
    }

    inner class UsersHolder(private val binding: HeaderUsersItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(userData: ItemType.HeaderUserList) {
            if (userListener.currentUser == VIEW_TYPE_SECOND_USER) {
                binding.firstUserButton.setBackgroundColor(Color.RED)
                binding.secondUserButton.setBackgroundColor(Color.BLUE)
            }

            with(binding) {
                firstUserButton.setOnClickListener {
                    userListener.userClick(VIEW_TYPE_FIRST_USER, binding)
                }

                firstUserButton.text = "User 1: ${userData.counterFirstUser}"

                secondUserButton.setOnClickListener {
                    userListener.userClick(VIEW_TYPE_SECOND_USER, binding)
                }

                secondUserButton.text = "User 2: ${userData.counterSecondUser}"
            }
        }
    }

    companion object  {
        val diff = object : DiffUtil.ItemCallback<ItemType>() {
            override fun areItemsTheSame(oldItem: ItemType, newItem: ItemType): Boolean {
                return when (oldItem) {
                    is ItemType.FirstUser -> oldItem == (newItem as? ItemType.FirstUser)
                    is ItemType.SecondUser -> oldItem == (newItem as? ItemType.SecondUser)
                    is ItemType.HeaderUserList -> false
                }
            }

            override fun areContentsTheSame(oldItem: ItemType, newItem: ItemType): Boolean {
                return when (oldItem) {
                    is ItemType.FirstUser ->
                        oldItem.message == (newItem as ItemType.FirstUser).message
                    is ItemType.SecondUser ->
                        oldItem.message == (newItem as ItemType.SecondUser).message
                    is ItemType.HeaderUserList ->
                        (oldItem.counterFirstUser == (newItem as ItemType.HeaderUserList).counterFirstUser
                                && oldItem.counterSecondUser == newItem.counterSecondUser)
                }
            }
        }

        const val VIEW_TYPE_FIRST_USER = 0
        const val VIEW_TYPE_SECOND_USER = 1
        const val VIEW_TYPE_HEADER_USERS = 2
    }
}