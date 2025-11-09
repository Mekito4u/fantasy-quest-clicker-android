package com.example.fantasyquestclicker.domain.utils

import com.example.fantasyquestclicker.R

// Функция для получения фона в зависимости от текущего уровня
fun getBackgroundForStage(stage: Int): Int {
    val backgroundIndex = (stage - 1) / 5 + 1
    return when (backgroundIndex) {
        1 -> R.drawable.stage5
        2 -> R.drawable.stage10
        3 -> R.drawable.stage15
        4 -> R.drawable.stage20
        5 -> R.drawable.stage25
        else -> R.drawable.stage30
    }
}