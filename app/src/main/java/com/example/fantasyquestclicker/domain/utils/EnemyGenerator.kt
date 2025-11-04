package com.example.fantasyquestclicker.domain.utils

import com.example.fantasyquestclicker.domain.models.Enemy
import kotlin.random.Random

object EnemyGenerator {
    private const val BASE_HP = 30
    private const val BASE_DAMAGE = 5
    private const val BASE_REWARD = 10
    private const val MIN_ATTACK_INTERVAL = 3000L
    private const val MAX_ATTACK_INTERVAL = 6000L

    private val enemyNames = listOf("Скелет", "Гоблин", "Орк", "Зомби", "Волк")
    private val enemyImages = listOf("skeleton", "goblin", "orc", "zombie", "wolf")

    fun generateEnemy(stage: Int, isBoss: Boolean = false): Enemy {
        val stageMultiplier = 1.0 + (stage - 1) * 0.3
        val bossMultiplier = if (isBoss) 2.0 else 1.0

        val health = (BASE_HP * random(0.8, 1.2) * stageMultiplier * bossMultiplier).toInt()
        val damage = (BASE_DAMAGE * random(0.7, 1.3) * stageMultiplier * bossMultiplier).toInt()
        val reward = (BASE_REWARD * random(0.9, 1.1) * stageMultiplier * bossMultiplier).toInt()
        val attackInterval = (MIN_ATTACK_INTERVAL..MAX_ATTACK_INTERVAL).random()

        val nameIndex = Random.nextInt(enemyNames.size)
        val name = if (isBoss) "Босс ${enemyNames[nameIndex]}" else enemyNames[nameIndex]

        return Enemy(
            id = Random.nextInt(1000, 9999),
            name = name,
            currentHealth = health,
            maxHealth = health,
            baseReward = reward,
            imageRes = enemyImages[nameIndex],
            damage = damage,
            attackInterval = attackInterval
        )
    }

    private fun random(min: Double, max: Double): Double =
        min + (max - min) * Math.random()
}