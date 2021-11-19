package com.example.hw3.data

import com.example.hw3.model.ItemType
import com.example.hw3.model.Message

object Data {
    val data = mutableListOf(
        ItemType.HeaderUserList(0, 0),
        ItemType.FirstUser(Message(1, "Hello")),
        ItemType.SecondUser(Message(2, "Hey")),
        ItemType.FirstUser(Message(1, "Hello")),
        ItemType.FirstUser(Message(1, "Hello")),
        ItemType.SecondUser(Message(2, "Hey")),
        ItemType.SecondUser(Message(2, "Hey")))
}