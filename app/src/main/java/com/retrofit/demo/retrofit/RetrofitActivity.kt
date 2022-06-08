package com.retrofit.demo.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.retrofit.demo.databinding.ActivityRetrofitBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class RetrofitActivity : AppCompatActivity() {

    val binding: ActivityRetrofitBinding by lazy { ActivityRetrofitBinding.inflate(layoutInflater) }
    lateinit var service: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        service = ApiService.create()

        binding.btnRequest.setOnClickListener {
            search {
                binding.tvContent.text = it
            }
        }
    }

    fun search(result: (String?) -> Unit) {
        Executors.newSingleThreadExecutor().execute {
            service.search("鸿洋").enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    runOnUiThread {
                        result.invoke(response.body())
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                }

            })
        }
    }

}