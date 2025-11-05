package com.example.fantasyquestclicker.domain.models

enum class QuestType(
    val displayName: String,
    val description: String
) {
    KILL_COUNT("⚔️ ОХОТА", "Убейте врагов"),
    STAGE_PROGRESS("🏆 СТАДИИ", "Пройдите\nстадии"),
    GOLD_EARN("💰 ЗОЛОТО", "Заработайте золота"),
    TOTAL_KILLS("💀 УБИЙСТВА", "Убейте всего врагов"),
    UPGRADE_SKILLS("🚀 ПРОГРЕСС", "Прокачайте навыки")
}