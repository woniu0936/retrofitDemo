package com.retrofit.demo.custom

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class Http {

    private val client: OkHttpClient by lazy { OkHttpClient() }
    private val gson: Gson by lazy { Gson() }

    var baseUrl = "https://wanandroid.com/"

    /**
     * 创建接口对象
     */
    inline fun <reified T> create(): T {
        //调用Proxy.newProxyInstance创建接口的实例化对象
        return Proxy.newProxyInstance(
            T::class.java.classLoader,
            arrayOf<Class<*>>(T::class.java)
        ) { proxy, method, args ->
            return@newProxyInstance method.annotations
                .filterIsInstance<GET>()
                .takeIf { it.size == 1 }
                ?.let { invoke("$baseUrl${it[0].value}", method, args) }
        } as T

    }

    /**
     * 执行http请求
     */
    fun invoke(url: String, method: Method, args: Array<Any>): Any? = method.parameterAnnotations
        ?.takeIf { it.size == args.size }
        ?.mapIndexed { index, it -> Pair(it, args[index]) }
        ?.fold(url, ::parseUrl)
        ?.let { Request.Builder().url(it).build() }
        ?.let { client.newCall(it).execute().body()?.toString() }
        ?.let {
            println("response: $it")
            gson.fromJson(it, method.genericReturnType)
        }

    /**
     * 解析方法，拼接url
     */
    private fun parseUrl(acc: String, pair: Pair<Array<Annotation>, Any>) =
        pair.first.filterIsInstance<Field>()
            .first()
            .let { field ->
                if (acc.contains("?")) {
                    "$acc&${field.value}=${pair.second}"
                } else {
                    "$acc?${field.value}=${pair.second}"
                }
            }


}