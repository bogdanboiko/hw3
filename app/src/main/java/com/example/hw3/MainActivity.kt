package com.example.hw3

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.hw3.adapter.ChatAdapter
import com.example.hw3.adapter.UserChangeClickListener
import com.example.hw3.data.Data
import com.example.hw3.databinding.ActivityMainBinding
import com.example.hw3.databinding.HeaderUsersItemBinding
import com.example.hw3.model.ItemType
import com.example.hw3.model.Message

class MainActivity : AppCompatActivity(), UserChangeClickListener {
    private lateinit var binding: ActivityMainBinding
    private val baseAdapter = ChatAdapter(this as UserChangeClickListener)
    private var currentUser = 0
    private var firstUserMessageCount = 0
    private var secondUserMessageCount = 0
    private var messages = mutableListOf<ItemType>(ItemType.HeaderUserList(0, 0))

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
         initAdapter()
         setContentView(binding.root)
    }

    fun onSubmitClick(view: View?) {
        if (binding.messageEditText.text.isEmpty()) {
            return
        }

        val userMessage = when (currentUser) {
            ChatAdapter.VIEW_TYPE_FIRST_USER ->
                ItemType.FirstUser(Message(firstUserMessageCount++, binding.messageEditText.text.toString()))
            else -> ItemType.SecondUser(Message(secondUserMessageCount++, binding.messageEditText.text.toString()))
        }

        (messages[0] as ItemType.HeaderUserList).apply {
            counterFirstUser = firstUserMessageCount
            counterSecondUser = secondUserMessageCount
        }

        messages.add(userMessage)
        baseAdapter.submitList(messages.toList())
    }

    private fun initAdapter() {
        baseAdapter.submitList(messages.toList())

        binding.recyclerViewContainer.apply {
            adapter = baseAdapter
        }
    }

    override fun userClick(user: Int, binding: HeaderUsersItemBinding) {
        currentUser = user

        when(user) {
            ChatAdapter.VIEW_TYPE_FIRST_USER ->
                with(binding) {
                    firstUserButton.setBackgroundColor(Color.BLUE)
                    secondUserButton.setBackgroundColor(Color.RED)
                }
            ChatAdapter.VIEW_TYPE_SECOND_USER ->
                with(binding) {
                    firstUserButton.setBackgroundColor(Color.RED)
                    secondUserButton.setBackgroundColor(Color.BLUE)
                }
        }
    }
}