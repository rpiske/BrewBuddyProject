package com.example.brewbuddy

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    //TEST COMMENT

    val BASE_URL = "https://api.openbrewerydb.org/v1/"
    private val TAG = "MainActivity"
    val breweryLocations = ArrayList<Brewery>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val breweryLocationsAPI = retrofitBuilder.create(BreweryService::class.java)

        breweryLocationsAPI.getBreweryByZip("44107", 3).enqueue(object : Callback<List<Brewery>?> {
            override fun onResponse(call: Call<List<Brewery>?>, response: Response<List<Brewery>?>) {
                Log.d(TAG, "onResponse: $response")
                val body = response.body()
                if(body == null) {
                    Log.d(TAG, "Valid response was not received")
                    return
                }
                breweryLocations.addAll(body)
                Log.d(TAG, "a: ${breweryLocations[0].name}")
                Log.d(TAG, "b: ${breweryLocations[1].name}")
                Log.d(TAG, "c: ${breweryLocations[2].name}")
            }

            override fun onFailure(call: Call<List<Brewery>?>, t: Throwable) {
                Log.d(TAG, "onResponse: $t")
            }
        })
    }
}