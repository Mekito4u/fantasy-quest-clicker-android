package com.example.fantasyquestclicker.domain.use_cases

import com.example.fantasyquestclicker.domain.models.Player

class StageProgressUseCase {
    operator fun invoke(player: Player): Player {
        val newEnemiesDefeated = player.enemiesDefeated + 1

        return if (player.isBossFight) {
            player.copy(
                currentStage = player.currentStage + 1,
                enemiesDefeated = 0,
            )
        } else {
            player.copy(enemiesDefeated = newEnemiesDefeated)
        }
    }
}