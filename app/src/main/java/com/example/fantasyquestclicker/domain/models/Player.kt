package com.example.fantasyquestclicker.domain.models

data class Player(
    val gold: Int = 0,
    val baseAttack: Int = 1,
    val maxTime: Int = 60,
    val currentTime: Int = 60,
    val currentStage: Int = 1,
    val enemiesDefeated: Int = 0,
    val criticalChance: Double = 0.0
) {
    fun takeTimeDamage(damage: Int): Player {
        return copy(currentTime = (currentTime - damage).coerceAtLeast(0))
    }

    fun addTime(amount: Int): Player = copy(
        currentTime = (currentTime + amount).coerceAtMost(maxTime)
    )

    fun calculateDamage(): Int = if (Math.random() < criticalChance) baseAttack * 2 else baseAttack

    val isDefeated: Boolean get() = currentTime <= 0
    val isBossFight: Boolean get() = enemiesDefeated >= 10

    // Методы прокачки
    fun upgradeAttack(cost: Int): Player = copy(gold = gold - cost, baseAttack = baseAttack+1)
    fun upgradeTime(cost: Int): Player =
        copy(gold = gold - cost, maxTime = maxTime + 15, currentTime = currentTime + 15)
}