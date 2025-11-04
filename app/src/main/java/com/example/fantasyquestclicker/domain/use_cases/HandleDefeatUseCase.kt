package com.example.fantasyquestclicker.domain.use_cases

import com.example.fantasyquestclicker.domain.models.Player

class HandleDefeatUseCase {
    operator fun invoke(player: Player): Player {
        return player.copy(
            currentStage = 1,
            enemiesDefeated = 0,
            currentTime  = player.maxTime
        )
    }
}