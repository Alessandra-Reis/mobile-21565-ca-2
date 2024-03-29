//ALESSANDRA SILVA DOS REIS // ID 21565

package com.reis.kotlinyoutubebta

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // recyclerView_main.setBackgroundColor(Color.BLUE)

        recyclerView_main.layoutManager = LinearLayoutManager(this)
        // recyclerView_main.adapter = MainAdapter()

        fetchJson()
    }

    fun fetchJson() {
        println("Attempting to Fetch JSON")

        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                runOnUiThread {

                    recyclerView_main.adapter = MainAdapter(homeFeed)

                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }
}

