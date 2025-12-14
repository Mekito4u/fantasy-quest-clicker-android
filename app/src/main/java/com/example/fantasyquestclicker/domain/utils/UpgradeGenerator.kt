package com.example.fantasyquestclicker.domain.utils

import com.example.fantasyquestclicker.domain.models.Player
import com.example.fantasyquestclicker.domain.models.SkillType
import kotlin.math.pow

// Класс для генерации стоимости улучшений
object UpgradeGenerator {
    fun getUpgradeCost(player: Player, skill: SkillType): Int {
        return when (skill) {
            SkillType.ATTACK -> (8 * player.baseAttack.toDouble().pow(1.3)).toInt()
            SkillType.CRITICAL -> (80 * (player.criticalChance*100).pow(1.7)).toInt()
            SkillType.TIME -> (10 * player.maxTime.toDouble().pow(1.85)).toInt()
        }
    }
}