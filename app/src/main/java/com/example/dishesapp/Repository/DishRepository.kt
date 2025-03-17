package com.example.dishesapp.Repository

import com.example.dishesapp.ApiService.DishApiService
import com.example.dishesapp.data.Dish
import retrofit2.Call
import javax.inject.Inject

class DishRepository @Inject constructor(private val apiService: DishApiService) {
    fun getDishes(): Call<List<Dish>> {
        return apiService.getDishes()
    }
}