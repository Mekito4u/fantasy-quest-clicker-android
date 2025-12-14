package com.example.fantasyquestclicker.domain.models

/**
 * Модель игрока. Хранит характеристики, состояние и статистику.
 * @property gold Текущее золото для покупок.
 * @property baseAttack Базовый урон атаки.
 * @property maxTime Максимальный запас времени на уровне.
 * @property currentTime Текущий запас времени. При 0 — поражение.
 * @property currentStage Текущий этап (локация).
 * @property totalKillsEnemy Счётчик убийств врагов для квестов.
 * @property totalKills Общий счётчик убийств.
 * @property enemiesDefeated Побеждено врагов на этапе. При 10 — босс.
 * @property criticalChance Шанс крит. удара (0.0 до 1.0).
 * @property upgradeSkills Куплено улучшений.
 * @property totalGoldEarned Всего золота заработано.
 */
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
    val totalGoldEarned: Int = 0
) {
    /**
     * Рассчитывает урон атаки с учётом шанса крита.
     */
    fun calculateDamage(): Int = if (Math.random() < criticalChance) baseAttack * 2 else baseAttack

    /**
     * true, если время игрока вышло (currentTime <= 0).
     */
    val isDefeated: Boolean get() = currentTime <= 0

    /**
     * true, если побеждено 10 врагов (пора сражаться с боссом).
     */
    val isBossFight: Boolean get() = enemiesDefeated >= 10
}