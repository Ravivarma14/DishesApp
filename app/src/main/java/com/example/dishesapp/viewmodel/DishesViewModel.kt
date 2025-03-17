package com.example.dishesapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dishesapp.Repository.DishRepository
import com.example.dishesapp.data.Dish
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DishesViewModel @Inject constructor(private val repository: DishRepository): ViewModel() {


//    val dishes = MutableLiveData<List<Dish>>()

    private val _dishes = MutableStateFlow<List<Dish>>(emptyList())
    val dishes: StateFlow<List<Dish>> = _dishes


    val errorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>(false)
    val apiResponseReceived = MutableLiveData<Boolean>(false)

    fun fetchDishes() {
        isLoading.value = true
        val call = repository.getDishes()
        call.enqueue(object : Callback<List<Dish>> {
            override fun onResponse(call: Call<List<Dish>>, response: Response<List<Dish>>) {
                isLoading.value = false
                Log.d("TAG", "onResponse: response: "+ response.body())
                if (response.isSuccessful) {
                    _dishes.value = response.body()!!
                    apiResponseReceived.value = true
                } else {
                    errorMessage.value = "Failed to fetch dishes: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<Dish>>, t: Throwable) {
                isLoading.value = false
                errorMessage.value = "Network error: ${t.message}"
                Log.d("TAG", "onFailure: error: "+ t.message)
            }
        })
    }

    fun updateDishSchedule(id:String, selectedHour:Int, selectedMinute:Int, isAM:Boolean){
        if(dishes.value?.size!! > 0 ){
            _dishes.value = _dishes.value!!.map { dish ->
                if (dish.id.equals(id)) dish.copy(selectedHour= selectedHour, selectedMinute = selectedMinute, isAM = isAM) else dish
            }
        }
    }

}
