package com.example.dishesapp.uiComponents

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.dishesapp.R
import com.example.dishesapp.data.Dish
import com.example.dishesapp.ui.theme.VoiletColor
import com.example.dishesapp.viewmodel.DishesViewModel

@Composable
    public fun TopBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Search Bar
            SearchBar(
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Scheduled Item Section

            val dishesViewModel: DishesViewModel = viewModel()

            val dishes2 by dishesViewModel.dishes.collectAsState()
            val earliestDish = dishes2
                ?.filter { it.selectedHour != 0 || it.selectedMinute != 0 } // Omit unscheduled dishes
                ?.minByOrNull { it.selectedHour * 60 + it.selectedMinute }
            val apiResponseReceived by dishesViewModel.apiResponseReceived.observeAsState(initial = false)

            if (apiResponseReceived) {
                Log.d("TAG", "DishesScreen: apiResponse: "+ apiResponseReceived)
//                Log.d("TAG", "DishesScreen: response: "+ dishesViewModel.dishes.value)
                ScheduledItem(earliestDish) // Render only after response
            }
            else{
                ScheduledItem(earliestDish = null)
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Notification + Power Icons
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = VoiletColor,
                        modifier = Modifier.size(28.dp)
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id= R.drawable.power_settings_new_24),
                        contentDescription = "Power",
                        tint = Color(0xFFFF5722),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }


    @Composable
    fun SearchBar(modifier: Modifier = Modifier) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(text= "Search for dish or ingredient",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.wrapContentWidth()) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = VoiletColor,
                )
            },
            shape = RoundedCornerShape(40.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = VoiletColor,
                unfocusedBorderColor = VoiletColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth()
        )
    }


    @Composable
    fun ScheduledItem(earliestDish: Dish?) {

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color(0xFF142832), RoundedCornerShape(35.dp))
                .padding(top = 5.dp, bottom = 5.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Dish Image
            PulsatingScheduledItem(earliestDish?.imageUrl)

            Spacer(modifier = Modifier.width(8.dp))

            // Dish Name, Time
            Column {
                Text(
                    text = earliestDish?.dishName ?: "Italian Spaghetti...",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(
                    text = if(earliestDish!=null)("Scheduled "+earliestDish?.selectedHour+":"+ earliestDish?.selectedMinute+ if(earliestDish?.isAM == true) " AM" else " PM") else "Scheduled 6:30 AM",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }

@Composable
fun PulsatingScheduledItem(imageUrl:String?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(35.dp)
    ) {
        val infiniteTransition = rememberInfiniteTransition()

        val pulseAlpha by infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1300, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )

        val pulseSize by infiniteTransition.animateFloat(
            initialValue = 50f,
            targetValue = 85f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1300, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )

        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = Color(0xFF9f86c3).copy(alpha = 0.5f),
                radius = 50f
            )
        }

        // Pulsating Effect (Surrounds the Icon)
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = Color(0xFF9f86c3),
                radius = pulseSize,
                alpha = pulseAlpha
            )
        }
        if(imageUrl!=null) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "dishName",
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
            )
        }
        else {

            // Scheduled Item Icon
            Image(
                painter = painterResource(id = R.drawable.outdoor_grill_24), // Replace with your icon
                contentDescription = "Scheduled Dish",
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
            )
        }
    }
}



    @Preview(
        name = "Tablet Preview",
        device = "spec:width=700px,height=1000px,dpi=240",
        showBackground = true
    )
    @Composable
    fun PreviewTopBar(){
        TopBar()
    }
