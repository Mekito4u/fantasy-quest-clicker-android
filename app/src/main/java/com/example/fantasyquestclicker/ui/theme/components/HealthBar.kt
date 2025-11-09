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

// ПРОГРЕСС БАР ЗДОРОВЬЯ
@Composable
fun HealthBar(
    currentHealth: Int,
    maxHealth: Int,
    modifier: Modifier = Modifier
) {
    val progress = currentHealth.toFloat() / maxHealth.toFloat()
    val healthColor = when {
        progress > 0.6f -> Color.Green
        progress > 0.3f -> Color.Yellow
        else -> Color.Red
    }

    Column(modifier = modifier) {
        Text(
            text = "HP: $currentHealth/$maxHealth",
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