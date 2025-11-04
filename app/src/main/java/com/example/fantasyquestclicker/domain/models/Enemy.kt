package com.example.fantasyquestclicker.domain.models

data class Enemy(
    val id: Int,
    val name: String,
    val currentHealth: Int,
    val maxHealth: Int,
    val baseReward: Int,
    val imageRes: String,
    val damage: Int,
    val attackInterval: Long
) {
    val healthPercentage: Float get() = currentHealth.toFloat() / maxHealth.toFloat()
    val isDefeated: Boolean get() = currentHealth <= 0

    fun takeDamage(damage: Int): Enemy = copy(
        currentHealth = (currentHealth - damage).coerceAtLeast(0)
    )
}