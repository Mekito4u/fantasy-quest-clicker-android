package com.example.fantasyquestclicker.domain.utils

import com.example.fantasyquestclicker.domain.models.Enemy
import com.example.fantasyquestclicker.domain.utils.EnemyTypes.types
import kotlin.math.pow
import kotlin.random.Random

// Класс для генерации врагов
object EnemyGenerator {
    fun generateEnemy(stage: Int, isBoss: Boolean = false): Enemy {
        val health = (10 * stage.toDouble().pow(1.8)).toInt()
        val reward = (8 * stage.toDouble().pow(1.4)).toInt()

        val selectedType = types.random()
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
}