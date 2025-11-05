package com.example.fantasyquestclicker.domain.utils

import com.example.fantasyquestclicker.domain.models.Player
import com.example.fantasyquestclicker.domain.models.SkillType

object UpgradeGenerator {
    fun getUpgradeCost(player: Player, skill: SkillType): Int {
        return when (skill) {
            SkillType.ATTACK -> 25 + (player.baseAttack * 10)
            SkillType.TIME -> 250 + (player.maxTime * 5)
            SkillType.CRITICAL -> 100 + ((player.criticalChance * 100).toInt() * 50)
        }
    }
}