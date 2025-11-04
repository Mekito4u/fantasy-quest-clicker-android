package com.example.fantasyquestclicker.domain.utils

import com.example.fantasyquestclicker.domain.models.Enemy
import kotlin.random.Random

object EnemyGenerator {
    private const val BASE_HP = 30
    private const val BASE_REWARD = 10

    private val enemyNames = listOf("Скелет", "Гоблин", "Орк", "Зомби", "Волк")
    private val enemyImages = listOf("skeleton", "goblin", "orc", "zombie", "wolf")

    fun generateEnemy(stage: Int, isBoss: Boolean = false): Enemy {
        val stageMultiplier = 1.0 + (stage - 1) * 0.3
        val bossMultiplier = if (isBoss) 2.0 else 1.0

        val health = (BASE_HP * random(0.8, 1.2) * stageMultiplier * bossMultiplier).toInt()
        val reward = (BASE_REWARD * random(0.9, 1.1) * stageMultiplier * bossMultiplier).toInt()

        val nameIndex = Random.nextInt(enemyNames.size)
        val name = if (isBoss) "Босс ${enemyNames[nameIndex]}" else enemyNames[nameIndex]

        return Enemy(
            id = Random.nextInt(1000, 9999),
            name = name,
            currentHealth = health,
            maxHealth = health,
            baseReward = reward,
            imageRes = enemyImages[nameIndex],
        )
    }

    private fun random(min: Double, max: Double): Double =
        min + (max - min) * Math.random()
}