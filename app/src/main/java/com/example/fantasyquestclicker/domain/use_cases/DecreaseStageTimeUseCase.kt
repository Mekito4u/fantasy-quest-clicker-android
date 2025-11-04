package com.example.fantasyquestclicker.domain.use_cases

import com.example.fantasyquestclicker.domain.models.Player

class DecreaseStageTimeUseCase {
    operator fun invoke(player: Player): Player {
        return if (player.currentTime > 0) {
            player.copy(currentTime = player.currentTime - 1)
        } else {
            player
        }
    }
}