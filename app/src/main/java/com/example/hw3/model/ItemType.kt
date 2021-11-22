package com.example.hw3.model

sealed class ItemType {
    data class FirstUser(val message: Message) : ItemType()

    data class SecondUser(val message: Message) : ItemType()

    data class HeaderUserList(var counterFirstUser: Int, var counterSecondUser: Int) : ItemType()
}

