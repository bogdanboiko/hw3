package com.example.hw3.model

sealed class ItemType() {
    class FirstUser(val message: Message) : ItemType()

    class SecondUser(val message: Message) : ItemType()

    class HeaderUserList(var counterFirstUser: Int, var counterSecondUser: Int) : ItemType()
}

