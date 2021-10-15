package com.vipulsaluja.paging3demo.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Vipul Saluja on 14-10-2021.
 */
class RetrofitClient {

    private lateinit var retrofit: Retrofit

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
    }

    fun getNetworkApi(): NetworkApi {
        return retrofit.create(NetworkApi::class.java)
    }
}