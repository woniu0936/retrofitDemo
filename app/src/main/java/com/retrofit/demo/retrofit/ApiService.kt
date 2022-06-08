package com.retrofit.demo.retrofit

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("article/list/0/json")
    fun search(@Query("author") key: String): Call<String>

    companion object {

        fun create(): ApiService {
            val client = OkHttpClient.Builder()
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://wanandroid.com/")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }

    }

}