package com.example.fantasyquestclicker.domain.utils

object EnemyTypes {
    val types = listOf(
        EnemyType("Скелет", "💀"),
        EnemyType("Гоблин", "\uD83E\uDDCC"),
        EnemyType("Орк", "👹"),
        EnemyType("Зомби", "🧟"),
        EnemyType("Волк", "🐺"),
        EnemyType("Дракон", "\uD83D\uDC32"),
    )

    data class EnemyType(
        val name: String,
        val emoji: String
    )
}