package com.example.hw3

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hw3.adapter.ChatAdapter
import com.example.hw3.adapter.UserChangeClickListener
import com.example.hw3.databinding.ActivityMainBinding
import com.example.hw3.databinding.HeaderUsersItemBinding
import com.example.hw3.decorator.MessageDecorator
import com.example.hw3.model.ItemType
import com.example.hw3.model.Message

class MainActivity : AppCompatActivity(), UserChangeClickListener {
    private lateinit var binding: ActivityMainBinding
    private val baseAdapter = ChatAdapter(this as UserChangeClickListener)
    override var currentUser = ChatAdapter.VIEW_TYPE_FIRST_USER
    private var firstUserMessageCount = 0
    private var secondUserMessageCount = 0
    private var messages = mutableListOf<ItemType>(ItemType.HeaderUserList(0, 0))

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)

         binding.submitButton.setOnClickListener {
             onSubmitClick()
         }

         initAdapter()
         setContentView(binding.root)
    }

    private fun onSubmitClick() {
        if (binding.messageEditText.text.isEmpty()) {
            return
        }

        val userMessage = when (currentUser) {
            ChatAdapter.VIEW_TYPE_FIRST_USER ->
                ItemType.FirstUser(Message(firstUserMessageCount++, binding.messageEditText.text.toString()))
            else -> ItemType.SecondUser(Message(secondUserMessageCount++, binding.messageEditText.text.toString()))
        }

        binding.messageEditText.text.clear()
        updateCounters()
        messages.add(userMessage)
        baseAdapter.submitList(messages.toList())
    }

    private fun initAdapter() {
        binding.recyclerViewContainer.apply {
            adapter = baseAdapter
            addItemDecoration(MessageDecorator(20))
        }

        baseAdapter.submitList(messages.toList())
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

    override fun itemLongPress(item: ItemType, user: Int) {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setMessage("Do you want to delete message?")
            .setPositiveButton("Yes"
            ) { dialog, _ ->
                dialogYesClick(item, user, dialog)
            }
            .setNegativeButton("No"
            ) { dialog, _ ->
                dialog.dismiss()
            }

        builder.show()
    }

    private fun dialogYesClick(item: ItemType, user: Int, dialog: DialogInterface) {
        when (user) {
            ChatAdapter.VIEW_TYPE_FIRST_USER ->
                messages.remove(item as ItemType.FirstUser).also { firstUserMessageCount-- }
            ChatAdapter.VIEW_TYPE_SECOND_USER ->
                messages.remove(item as ItemType.SecondUser).also { secondUserMessageCount-- }
        }

        updateCounters()
        baseAdapter.submitList(messages.toList())
        dialog.dismiss()
    }

    private fun updateCounters() {
        (messages[0] as ItemType.HeaderUserList).apply {
            counterFirstUser = firstUserMessageCount
            counterSecondUser = secondUserMessageCount
        }
    }
}