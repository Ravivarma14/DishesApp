package com.example.dishesapp.ApiService

import android.util.Log
import com.example.dishesapp.data.Dish
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

val BASE_URL="https://fls8oe8xp7.execute-api.ap-south-1.amazonaws.com/dev/"
interface DishApiService {
    @GET("nosh-assignment") //  endpoint
    fun getDishes(): Call<List<Dish>>
}

object RetrofitInstance {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Logs API requests for Debugging
            Log.d("TAG", "msg: : "+level)
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val api: DishApiService = retrofit.create(DishApiService::class.java)
}
