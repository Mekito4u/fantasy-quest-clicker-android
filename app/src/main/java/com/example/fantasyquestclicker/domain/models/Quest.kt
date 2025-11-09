package com.example.fantasyquestclicker.domain.models

// Модель квеста
data class Quest(
    val type: QuestType,
    val targetValue: Int,
    val targetName: String = "",
    val isCompleted: Boolean = false,
    val reward: Int
)