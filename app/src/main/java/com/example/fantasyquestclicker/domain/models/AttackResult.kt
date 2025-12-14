package com.example.fantasyquestclicker.domain.models

/**
 * Модель, содержащая результаты выполнения одной атаки.
 * Используется для передачи данных из Use Case в ViewModel.
 * @property damageDealt Величина нанесённого врагу урона.
 * @property isEnemyDefeated true, если атака привела к победе над врагом.
 * @property goldReward Количество золота, полученное за победу (0, если враг не повержен).
 * @property updatedPlayer Обновлённое состояние игрока после атаки.
 * @property updatedEnemy Обновлённое состояние врага после получения урона.
 */
data class AttackResult(
    val damageDealt: Int,
    val isEnemyDefeated: Boolean,
    val goldReward: Int,
    val updatedPlayer: Player,
    val updatedEnemy: Enemy
)