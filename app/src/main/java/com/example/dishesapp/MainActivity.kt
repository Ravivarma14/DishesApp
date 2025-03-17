package com.example.dishesapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dishesapp.data.Dish
import com.example.dishesapp.ui.theme.DishesAppTheme
import com.example.dishesapp.uiComponents.RecommendationsSection
import com.example.dishesapp.uiComponents.SideNavBar
import com.example.dishesapp.uiComponents.TopBar
import com.example.dishesapp.viewmodel.DishesViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DishesAppTheme {
                SetStatusBarColor(color = MaterialTheme.colorScheme.background)

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DishesScreen()
                }
            }
        }
    }
}

@Composable
fun DishesScreen(){

    SideNavBar()
}

@Composable
fun SetStatusBarColor(color: Color){
    val systemUiController= rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(color=color)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DishesAppTheme {
        val viewModel: DishesViewModel = hiltViewModel()
        RecommendationsSection(viewModel)
    }
}