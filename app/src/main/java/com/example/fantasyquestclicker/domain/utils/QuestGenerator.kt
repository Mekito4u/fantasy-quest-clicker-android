package com.example.fantasyquestclicker.domain.utils

import com.example.fantasyquestclicker.domain.models.Player
import com.example.fantasyquestclicker.domain.models.Quest
import com.example.fantasyquestclicker.domain.models.QuestType

// Класс для генерации квестов
object QuestGenerator {
    private var generatedQuests: List<Quest> = emptyList()

    // Возвращает список сгенерированных квестов
    fun getQuests(player: Player): List<Quest> {
        if (generatedQuests.isEmpty()) {
            generatedQuests = QuestType.entries.map { type ->
                generateQuest(player, type)
            }
        }
        return generatedQuests
    }

    // Возвращает текущий квест по типу
    fun getQuest(questType: QuestType): Quest? {
        return generatedQuests.firstOrNull { it.type == questType }
    }

    // Возвращает прогресс выполнения квеста
    fun getQuestProgress(player: Player, questType: QuestType): Int {
        return when (questType) {
            QuestType.KILL_COUNT -> player.totalKillsEnemy
            QuestType.STAGE_PROGRESS -> player.currentStage
            QuestType.GOLD_EARN -> player.gold
            QuestType.TOTAL_KILLS -> player.totalKills
            QuestType.UPGRADE_SKILLS -> player.upgradeSkills
        }
    }

    // Генерирует новый квест по типу
    private fun generateQuest(player: Player, questType: QuestType): Quest {
        val target = when (questType) {
            QuestType.KILL_COUNT -> (5 + player.currentStage..20 + player.currentStage * 2).random()
            QuestType.STAGE_PROGRESS -> (1 + player.currentStage..5 + player.currentStage).random()
            QuestType.GOLD_EARN -> maxOf(500, (player.totalGoldEarned * 1.15).toInt())
            QuestType.TOTAL_KILLS -> maxOf(500, (player.totalKills * 1.15).toInt())
            QuestType.UPGRADE_SKILLS -> maxOf(5, (player.upgradeSkills * 1.20).toInt())
        }
        val reward = when (questType) {
            QuestType.KILL_COUNT -> target * 2 + player.currentStage * 5
            QuestType.STAGE_PROGRESS -> target * 10 + player.currentStage * 20
            QuestType.GOLD_EARN -> (target * 0.1).toInt()
            QuestType.TOTAL_KILLS -> (target * 0.1).toInt()
            QuestType.UPGRADE_SKILLS -> target * 15 + player.currentStage * 25
        }

        val targetName = if (questType == QuestType.KILL_COUNT) {
            EnemyTypes.types.random().name
        } else {
            ""
        }

        // Возвращает сгенерированный квест
        return Quest(
            targetName = targetName,
            type = questType,
            targetValue = target,
            reward = reward
        )
    }

    // Заменяет текущий квест на новый
    fun replaceQuest(player: Player, questType: QuestType) {
        generatedQuests = generatedQuests.map {
            if (it.type == questType) generateQuest(player, questType) else it
        }
    }

    // Восстанавливает список квестов
    fun restoreQuests(quests: List<Quest>) {
        generatedQuests = quests
    }
}