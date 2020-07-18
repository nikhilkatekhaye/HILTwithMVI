package com.org.hilt_android_demo.retrofit

import retrofit2.http.GET

interface BlogRetrofit {

    @GET("blogs")
    suspend fun get() : List<BlogNetworkEntity>
}