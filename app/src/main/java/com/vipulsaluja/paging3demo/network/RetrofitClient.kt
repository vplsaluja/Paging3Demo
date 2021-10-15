package com.vipulsaluja.paging3demo.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Vipul Saluja on 14-10-2021.
 */
object RetrofitClient {

    private const val BASE_URL="https://api.github.com/"

    fun getNetworkApi(): NetworkApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(NetworkApi::class.java)
    }
}