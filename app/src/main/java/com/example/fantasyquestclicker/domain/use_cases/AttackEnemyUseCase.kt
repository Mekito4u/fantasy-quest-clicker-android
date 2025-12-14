package com.example.fantasyquestclicker.domain.use_cases

import com.example.fantasyquestclicker.domain.models.Enemy
import com.example.fantasyquestclicker.domain.models.Player
import com.example.fantasyquestclicker.domain.models.AttackResult

/**
 * Сценарий использования (Use Case) для атаки врага игроком.
 * Содержит основную логику боя: расчёт урона, обновление состояний, начисление награды.
 */
class AttackEnemyUseCase {
    /**
     * Выполняет атаку игрока по врагу и возвращает результат.
     * @param player Текущее состояние игрока.
     * @param enemy Текущее состояние атакуемого врага.
     * @return Результат атаки [AttackResult] с обновлёнными данными.
     */
    operator fun invoke(player: Player, enemy: Enemy): AttackResult {
        val damage = player.calculateDamage()
        val updatedEnemy = enemy.takeDamage(damage)
        val isEnemyDefeated = updatedEnemy.isDefeated

        val updatedPlayer = if (isEnemyDefeated) {
            player.copy(
                gold = player.gold + enemy.baseReward,
                totalGoldEarned = player.totalGoldEarned + enemy.baseReward
            )
        } else {
            player
        }

        return AttackResult(
            damageDealt = damage,
            isEnemyDefeated = isEnemyDefeated,
            goldReward = if (isEnemyDefeated) enemy.baseReward else 0,
            updatedPlayer = updatedPlayer,
            updatedEnemy = updatedEnemy
        )
    }
}