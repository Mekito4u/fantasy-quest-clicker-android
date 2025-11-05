package com.example.fantasyquestclicker.domain.utils

import com.example.fantasyquestclicker.domain.models.Enemy
import kotlin.random.Random

object EnemyGenerator {
    fun generateEnemy(stage: Int, isBoss: Boolean = false): Enemy {
        val health = 10 + (stage * 20)
        val reward = 10 + (stage * 5)

        val enemyTypes = listOf(
            EnemyType("Скелет", "💀"),
            EnemyType("Гоблин", "\uD83E\uDDCC"),
            EnemyType("Орк", "👹"),
            EnemyType("Зомби", "🧟"),
            EnemyType("Волк", "🐺"),
            EnemyType("Дракон", "\uD83D\uDC32"),
        )

        val selectedType = enemyTypes.random()
        val name = if (isBoss) "Босс ${selectedType.name}" else selectedType.name

        return Enemy(
            id = Random.nextInt(1000, 9999),
            name = name,
            currentHealth = if (isBoss) health * 3 else health,
            maxHealth = if (isBoss) health * 3 else health,
            baseReward = if (isBoss) reward * 3 else reward,
            imageRes = selectedType.emoji
        )
    }

    private data class EnemyType(
        val name: String,
        val emoji: String
    )
}