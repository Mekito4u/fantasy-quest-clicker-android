package com.example.fantasyquestclicker.domain.use_cases

import com.example.fantasyquestclicker.domain.models.Enemy
import com.example.fantasyquestclicker.domain.models.Player

class EnemyAttackUseCase {
    operator fun invoke(player: Player, enemy: Enemy): Player {
        return player.takeTimeDamage(enemy.damage)
    }
}