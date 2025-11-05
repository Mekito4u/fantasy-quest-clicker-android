package com.example.fantasyquestclicker.domain.models
import com.example.fantasyquestclicker.domain.utils.UpgradeGenerator

enum class SkillType(
    val displayName: String,
    val description: String
) {
    ATTACK("⚔️ АТАКА", "Увеличивает урон на +5 за уровень"),
    TIME("⏰ ВРЕМЯ", "Добавляет +5 секунд к времени"),
    CRITICAL("🎯 КРИТ", "Увеличивает шанс на +1%");

    fun getUpgradeCost(player: Player): Int {
        return UpgradeGenerator.getUpgradeCost(player, this)
    }
}

fun getCurrentSkillValue(player: Player, skill: SkillType): String {
    return when (skill) {
        SkillType.ATTACK -> "${player.baseAttack} урона"
        SkillType.TIME -> "${player.maxTime} сек"
        SkillType.CRITICAL -> "${(player.criticalChance * 100).toInt()}%"
    }
}