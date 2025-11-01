package com.example.fantasyquestclicker.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp),
            color = healthColor,
        )
    }
}