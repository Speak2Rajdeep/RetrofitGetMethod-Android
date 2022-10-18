package com.example.retrofit_get

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseURL = "https://jsonplaceholder.typicode.com/"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getMyData()
    }

    private fun getMyData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>> {
            override fun onResponse(
                call: Call<List<MyDataItem>>,
                response: Response<List<MyDataItem>>) {
                val responseBody = response.body()!!
                val strBuilder = StringBuilder()

                for(data in responseBody){
                    strBuilder.append(data.id)
                    strBuilder.append("  ")
                    strBuilder.append(data.title)
                    strBuilder.append("\n")
                }
                val textView = findViewById<TextView>(R.id.textview)
                textView.text = strBuilder
            }

            override fun onFailure(call: Call<List<MyDataItem>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Wrong : $(t.message)",Toast.LENGTH_LONG).show()
            }
        })

    }
}