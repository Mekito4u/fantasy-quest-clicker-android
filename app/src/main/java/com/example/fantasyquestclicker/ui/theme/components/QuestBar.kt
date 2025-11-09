package com.example.fantasyquestclicker.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// ПРОГРЕСС БАР КВЕСТОВ
@Composable
fun QuestBar(
    targetValue: Int,
    completedCount: Int,
    modifier: Modifier = Modifier
) {
    val progress = completedCount.toFloat() / targetValue.toFloat()
    val healthColor = when {
        progress > 0.6f -> Color.Red
        progress > 0.3f -> Color.Yellow
        else -> Color.Green
    }

    Column(modifier = modifier) {
        Text(
            text = "ПРОГРЕСС: $completedCount/$targetValue",
            color = Color.LightGray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // ОКРУГЛЫЙ PROGRESS BAR
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF444444))
        ) {
            // Заливка здоровья
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(10.dp))
                    .background(healthColor)
            )
        }
    }
}