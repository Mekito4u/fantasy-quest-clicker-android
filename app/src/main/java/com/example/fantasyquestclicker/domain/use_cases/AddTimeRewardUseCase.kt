package com.example.fantasyquestclicker.domain.use_cases

import com.example.fantasyquestclicker.domain.models.Player

class AddTimeRewardUseCase {
    operator fun invoke(player: Player, isBoss: Boolean): Player {
        val rewardTime = if (isBoss) 30 else 5
        return player.addTime(rewardTime)
    }
}