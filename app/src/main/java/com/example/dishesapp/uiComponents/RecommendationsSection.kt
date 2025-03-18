package com.example.dishesapp.uiComponents

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dishesapp.viewmodel.DishesViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.dishesapp.data.Dish
import com.example.dishesapp.ui.theme.VoiletColor
import com.example.dishesapp.R
import kotlinx.coroutines.launch

@Composable
fun DishItemCard(dish: Dish, isSelected: Boolean= false,onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(180.dp)
            .wrapContentHeight()
            .shadow(6.dp, shape = RoundedCornerShape(12.dp)),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = if(isSelected) VoiletColor else Color.White)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
//                .verticalScroll(rememberScrollState())
        ) {
            Box {
                // Dish Image
                Image(
                    painter = rememberAsyncImagePainter(dish.imageUrl),
                    contentDescription = dish.dishName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Crop
                )

                // Rating Container
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 10.dp) // Moves the box slightly down to overlap
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFF5722).copy(alpha = 0.9f)) // Semi-transparent yellow
                        .padding(horizontal = 6.dp, vertical = 0.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "4.5",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                // Dish Name
                Text(
                    text = dish.dishName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = if (isSelected) Color.White else VoiletColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Time & Prep Level Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.outdoor_grill_24),
                        contentDescription = "Time",
                        tint = Color.Gray,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "30 min",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Prep Level
                    Text(
                        text = "Medium Prep",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsSection(viewModel: DishesViewModel) {
//    val dishes by viewModel.dishes.observeAsState(initial = listOf())
    val dishes by viewModel.dishes.collectAsState()
    val errorMessage by viewModel.errorMessage.observeAsState(initial = null)
    val isLoading by viewModel.isLoading.observeAsState(false)

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var selectedDish by remember { mutableStateOf<Dish?>(null) }
    var selectedHour by remember { mutableStateOf(6) }
    var selectedMinute by remember { mutableStateOf(30) }
    var isAM by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Title Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Recommendations",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = VoiletColor
                )
                Text(
                    text = "Show All",
                    fontSize = 14.sp,
                    color = VoiletColor,
                    modifier = Modifier.clickable { }
                )
            }

            if (isLoading) {
                PulsingDot()
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else if (dishes.isEmpty()) {
                Text(
                    text = "No recommendations found.",
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
                    items(dishes) { dish ->
                        DishItemCard(dish = dish, selectedDish?.id.equals(dish.id),onClick = {
                            selectedDish = dish
                            coroutineScope.launch { bottomSheetState.show() }
                        })
                    }
                }
            }
        }

        // Bottom Sheet appears when a dish is selected
        if (selectedDish != null) {
            selectedHour= selectedDish!!.selectedHour
            selectedMinute= selectedDish!!.selectedMinute
            isAM= selectedDish!!.isAM

            ModalBottomSheet(
                onDismissRequest = { selectedDish = null },
                sheetState = bottomSheetState
            ) {
                ScheduleCookingBottomSheet(
                    dish = selectedDish!!,
                    selectedHour = selectedHour,
                    selectedMinute = selectedMinute,
                    isAM = isAM,
                    onHourChange = { selectedHour = it },
                    onMinuteChange = { selectedMinute = it },
                    onAMPMChange = { isAM = it },
                    onDelete = {
                        viewModel.updateDishSchedule(selectedDish!!.id, 0,0,true)
                        coroutineScope.launch { bottomSheetState.hide() }
                        selectedDish = null
                    },
                    onReschedule = {
                        viewModel.updateDishSchedule(selectedDish!!.id, selectedHour,selectedMinute,isAM)
                        coroutineScope.launch { bottomSheetState.hide() }
                        selectedDish = null
                    },
                    onCookNow = {
                        viewModel.updateDishSchedule(selectedDish!!.id, selectedHour,selectedMinute,isAM)
                        coroutineScope.launch { bottomSheetState.hide() }
                        selectedDish = null
                    }
                )
            }
        }
    }
}


@Composable
fun ScheduleCookingBottomSheet(
    dish: Dish,
    selectedHour: Int,
    selectedMinute: Int,
    isAM: Boolean,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit,
    onAMPMChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onReschedule: () -> Unit,
    onCookNow: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Schedule Cooking Time",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E2D5E)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Time Picker Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            TimePickerColumn((1..12).toList(), selectedHour) { onHourChange(it) }
            Text(
                text = ":",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            TimePickerColumn((0..59).toList(), selectedMinute) { onMinuteChange(it) }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                AMPMButton(text = "AM", isSelected = isAM, onClick = { onAMPMChange(true) })
                AMPMButton(text = "PM", isSelected = !isAM, onClick = { onAMPMChange(false) })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(onClick = onDelete) {
                Text(text = "Delete", color = Color.Red)
            }
            Button(onClick = onReschedule, colors = ButtonDefaults.buttonColors(Color(0xFFFF9800))) {
                Text(text = "Re-schedule")
            }
            Button(onClick = onCookNow, colors = ButtonDefaults.buttonColors(Color(0xFFFF9800))) {
                Text(text = "Cook Now")
            }
        }
    }
}


@Composable
fun TimePickerColumn(numbers: List<Int>, selected: Int, onSelect: (Int) -> Unit) {
    val listState = rememberLazyListState()

    // Scroll to the selected item when Composable is recomposed
    LaunchedEffect(selected) {
        val index = numbers.indexOf(selected)
        if (index != -1) {
            listState.animateScrollToItem(index)
        }
    }

    LazyColumn(
        state = listState, // Attach the LazyListState
        modifier = Modifier
            .height(100.dp)
            .width(60.dp)
    ) {
        items(numbers) { number ->
            Text(
                text = number.toString().padStart(2, '0'),
                fontSize = if (number == selected) 28.sp else 22.sp,
                fontWeight = if (number == selected) FontWeight.Bold else FontWeight.Normal,
                color = if (number == selected) Color(0xFF1E2D5E) else Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(number) }
                    .padding(4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun AMPMButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(if (isSelected) Color(0xFF1E2D5E) else Color.LightGray),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = text, color = if (isSelected) Color.White else Color(0xFF1E2D5E))
    }
}


@Composable
fun PulsingDot() {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(Color.Blue.copy(alpha = alpha))
    )
}



    @Preview(showBackground = true, widthDp = 700, heightDp = 400)
    @Composable
    fun PreviewRecommendationsSection() {

    }

