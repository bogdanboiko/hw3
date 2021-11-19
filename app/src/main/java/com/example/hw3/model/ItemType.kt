package com.example.hw3.model

sealed class ItemView() {
    class FirstUser(val message: Message) : ItemView()

    class SecondUser(val message: Message) : ItemView()

    class HeaderUserList(var counterFirstUser: Int, var counterSecondUser: Int) : ItemView()
}

