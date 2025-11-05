package com.example.fantasyquestclicker.domain.models

data class Player(
    val gold: Int = 0,
    val baseAttack: Int = 5,
    val maxTime: Int = 10,
    val currentTime: Int = 10,
    val currentStage: Int = 1,
    val totalKillsEnemy: Int = 0,
    val totalKills: Int = 0,
    val enemiesDefeated: Int = 0,
    val criticalChance: Double = 0.0,
    val upgradeSkills: Int = 0,
) {
    fun calculateDamage(): Int = if (Math.random() < criticalChance) baseAttack * 2 else baseAttack

    val isDefeated: Boolean get() = currentTime <= 0
    val isBossFight: Boolean get() = enemiesDefeated >= 10
}