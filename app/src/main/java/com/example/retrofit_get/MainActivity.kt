package com.example.retrofit_get

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseURL = "https://jsonplaceholder.typicode.com/"

class MainActivity : AppCompatActivity() {

    private var responseBody: ArrayList<MyDataItem> = ArrayList()
    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var progressBar : ProgressBar
    lateinit var loading : TextView
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progress)
        loading = findViewById(R.id.loading)
        recyclerView = findViewById(R.id.recycler)

        recycler.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recycler.layoutManager = linearLayoutManager

        myAdapter = MyAdapter(baseContext, responseBody)
        getMyData()
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>> {
            override fun onResponse(
                call: Call<List<MyDataItem>>,
                response: Response<List<MyDataItem>>
            ) {
                response.body()?.let { responseBody.addAll(it) }

                myAdapter.notifyDataSetChanged()
                recycler.adapter = myAdapter
                progressBar.visibility = View.GONE
                loading.visibility = View.GONE
                recyclerView.visibility  = View.VISIBLE

            }

            override fun onFailure(call: Call<List<MyDataItem>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Wrong : ${t.message}", Toast.LENGTH_LONG).show()
            }
        })

    }
}