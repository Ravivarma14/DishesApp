package com.example.dishesapp.uiComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dishesapp.R
import com.example.dishesapp.ui.theme.VoiletColor

@Composable
fun WhatsOnYourMindSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        // Title
        Text(
            text = "What's on your mind?",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = VoiletColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Mind Items - horizontal list
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(getMindItems()) { item ->
                MindItemCard(item)
            }
        }
    }
}

@Composable
fun MindItemCard(item: MindItem) {

    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(30.dp), // Adding shadow
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .wrapContentSize()
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(15.dp),
                ambientColor = Color(0xFFFF3C01),
                spotColor = Color(0xFFFF3C01)
            )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(end= 5.dp)
                .fillMaxWidth()
        ) {
            // Image inside circular frame
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = item.name,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape), // Optional background
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(5.dp))

            // Text beside the image
            Text(
                text = item.name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = VoiletColor
            )
        }
    }
}



@Preview
@Composable
fun PreviewFilterSection(){
    WhatsOnYourMindSection()
}

// Data Class for Items
data class MindItem(val icon: Int, val name: String)

fun getMindItems(): List<MindItem> {
    return listOf(
        MindItem(R.drawable.rice, "Rice items"),
        MindItem(R.drawable.indian, "Indian"),
        MindItem(R.drawable.curries, "Curries"),
        MindItem(R.drawable.soups, "Soups"),
        MindItem(R.drawable.desserts, "Desserts"),
        MindItem(R.drawable.snacks, "Snacks")
    )
}
