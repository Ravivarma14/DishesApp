package com.example.dishesapp.data

import com.google.gson.annotations.SerializedName

data class Dish(
    @SerializedName("dishName") val dishName: String,
    @SerializedName("dishId") val id: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("isPublished") val isPublished: Boolean,

    val selectedHour:Int=0,
    val selectedMinute:Int=0,
    val isAM:Boolean=true
)
