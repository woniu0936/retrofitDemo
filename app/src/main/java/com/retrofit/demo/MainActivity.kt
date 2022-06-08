package com.retrofit.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.retrofit.demo.custom.CustomActivity
import com.retrofit.demo.databinding.ActivityMainBinding
import com.retrofit.demo.retrofit.RetrofitActivity
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btn01.setOnClickListener {
            Intent(this, RetrofitActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.btn02.setOnClickListener {
            Intent(this, CustomActivity::class.java).apply {
                startActivity(this)
            }
        }

    }
}