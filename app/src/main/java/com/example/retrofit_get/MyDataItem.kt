package com.example.retrofit_get

//Creating Data Class of all the Response Fields
data class MyDataItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)
