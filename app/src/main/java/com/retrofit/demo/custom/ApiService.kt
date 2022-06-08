package com.retrofit.demo.custom

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("article/list/0/json")
    fun search(@Query("author") key: String): Call<String>

    companion object {
        fun create(): ApiService = Http().create()
    }

}