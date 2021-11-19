package com.example.hw3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hw3.adapter.ChatAdapter
import com.example.hw3.data.Data
import com.example.hw3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val baseAdapter = ChatAdapter()

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
         initAdapter()
         setContentView(binding.root)
    }

    private fun initAdapter() {
        baseAdapter.submitList(Data.data)
        binding.recyclerViewContainer.apply {
            adapter = baseAdapter
        }
    }
}