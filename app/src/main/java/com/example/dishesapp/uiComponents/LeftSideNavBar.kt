package com.example.dishesapp.uiComponents

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dishesapp.data.navigationItems
import com.example.dishesapp.ui.theme.VoiletColor
import com.example.dishesapp.viewmodel.DishesViewModel


@Composable
    fun SideNavBar(){
        var selectedItem by remember { mutableStateOf(0) }

        Row (modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier
                .fillMaxHeight()
                .width(80.dp)
                .verticalScroll(rememberScrollState())
                .background(Color.White), horizontalAlignment = Alignment.CenterHorizontally) {

                Spacer(modifier = Modifier.height(16.dp))

                navigationItems.forEachIndexed { index, navigationItem ->
                    NavigationItem(icon= navigationItem.icon,
                        title= navigationItem.title,
                        isSelected= selectedItem==index,
                        onClick= { selectedItem= index})
                }


                Spacer(modifier = Modifier.weight(1f))

            }


            // Content Area
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF6F8FB))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {

                /*if(navigationItems[selectedItem].title.equals("Cook")){
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)){

                        TopBar()

                        WhatsOnYourMindSection()

                        val dishesViewModel:DishesViewModel= viewModel()

                        Log.d("TAG", "DishesScreen: before")
                        dishesViewModel.fetchDishes()
                        Log.d("TAG", "DishesScreen: after")

                        val apiResponseReceived by dishesViewModel.apiResponseReceived.observeAsState(initial = false)

                        if (apiResponseReceived) {
                            Log.d("TAG", "DishesScreen: apiResponse: "+ apiResponseReceived)
//                            Log.d("TAG", "DishesScreen: response: "+ dishesViewModel.dishes.value)
                            RecommendationsSection(viewModel = dishesViewModel)
                        }
                        else {
                            RecommendationsSection(viewModel = dishesViewModel)
                        }

                        BottomCardsSection()

                    }
                }*/


                if (navigationItems[selectedItem].title.equals("Cook")) {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 12.dp)
                    ) {
                        item {
                            TopBar()
                        }

                        item {
                            WhatsOnYourMindSection()
                        }

                        item {
                            val dishesViewModel: DishesViewModel = viewModel()

                            Log.d("TAG", "DishesScreen: before")
                            dishesViewModel.fetchDishes()
                            Log.d("TAG", "DishesScreen: after")

                            val apiResponseReceived by dishesViewModel.apiResponseReceived.observeAsState(initial = false)

                            if (apiResponseReceived) {
                                Log.d("TAG", "DishesScreen: apiResponse: " + apiResponseReceived)
                                RecommendationsSection(viewModel = dishesViewModel)
                            } else {
                                RecommendationsSection(viewModel = dishesViewModel)
                            }
                        }

                        item {
                            BottomCardsSection()
                        }
                    }
                }


                else {
                    Text(
                        text = "Selected: ${navigationItems[selectedItem].title}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }


    @Composable
    fun NavigationItem(icon: Int, title: String, isSelected: Boolean, onClick: () -> Unit) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
                    .background(
                        Color.Transparent,
                    )
                    .padding(vertical = 12.dp)
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = title,
                    tint = if (isSelected) Color(0xFFFF5722) else VoiletColor,
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isSelected) Color(0xFFFF5722) else VoiletColor
                )
            }

    }

    @Preview
    @Composable
    fun PreviewSideNavBar(){
        SideNavBar()
    }