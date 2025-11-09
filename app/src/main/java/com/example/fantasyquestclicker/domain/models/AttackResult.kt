package com.example.fantasyquestclicker.domain.models

// Модель для хранения результатов атаки
data class AttackResult(
    val damageDealt: Int,
    val isEnemyDefeated: Boolean,
    val goldReward: Int,
    val updatedPlayer: Player,
    val updatedEnemy: Enemy
)