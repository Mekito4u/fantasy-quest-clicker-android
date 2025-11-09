package com.example.fantasyquestclicker.ui.theme.components

import android.annotation.SuppressLint
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun PlayerTimer(
    currentTime: Int,
) {
    Text(
        text = "⏰ ${formatTime(currentTime)}",
        color = Color.Black,
        fontSize = 22.sp
    )
}

@SuppressLint("DefaultLocale")
private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%d:%02d", minutes, remainingSeconds)
}