package com.example.retrofit_get

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    //Calling Get Method to fetch the data from the API Server
    @GET("posts")
    fun getData(): Call<List<MyDataItem>>
}