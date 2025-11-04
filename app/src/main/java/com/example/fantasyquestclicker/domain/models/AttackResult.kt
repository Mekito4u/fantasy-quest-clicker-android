package com.example.fantasyquestclicker.domain.models

data class AttackResult(
    val damageDealt: Int,
    val isEnemyDefeated: Boolean,
    val goldReward: Int,
    val updatedPlayer: Player,
    val updatedEnemy: Enemy
)