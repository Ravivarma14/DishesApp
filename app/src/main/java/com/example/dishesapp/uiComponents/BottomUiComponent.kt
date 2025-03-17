package com.example.dishesapp.uiComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dishesapp.R

@Composable
fun BottomCardsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomCard(
            title = "Explore All Dishes",
            backgroundImage = R.drawable.rice,
            false
        )

        Spacer(modifier = Modifier.width(20.dp))

        BottomCard(
            title = "Confused What to Cook?",
            backgroundImage = R.drawable.backgorundbtn
            ,true
        )
    }
}
@Composable
fun BottomCard(
    title: String,
    backgroundImage: Int,
    isCenter:Boolean = false
) {
    Card(
        modifier = Modifier
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFFFA726))
                .padding(start = if(!isCenter) 16.dp else 0.dp, end= 0.dp)
        ) {

            var horizontalArrangement= Arrangement.Start
            if(isCenter) {
                horizontalArrangement = Arrangement.Center

                Image(
                    painter = painterResource(id = backgroundImage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFFA726).copy(alpha = 0.6f))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = horizontalArrangement
                        ) {
                            Text(
                                text = title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        }
                    }
            }


                if(!isCenter) {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = horizontalArrangement
                    ) {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )

                    Spacer(modifier = Modifier.width(12.dp))
                    Image(
                        painter = painterResource(id = backgroundImage),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 24.dp,
                                    bottomStart = 24.dp,
                                    topEnd = 15.dp,
                                    bottomEnd = 15.dp
                                )
                            )
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true, widthDp = 700, heightDp = 400)
@Composable
fun PreviewBottomUI(){
    BottomCardsSection()
}
