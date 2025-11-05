package com.example.fantasyquestclicker.domain.utils

import com.example.fantasyquestclicker.domain.models.Player
import com.example.fantasyquestclicker.domain.models.Quest
import com.example.fantasyquestclicker.domain.models.QuestType

object QuestGenerator {
    private var generatedQuests: List<Quest> = emptyList()

    fun getQuests(player: Player): List<Quest> {
        if (generatedQuests.isEmpty()) {
            generatedQuests = QuestType.entries.map { type ->
                generateQuest(player, type)
            }
        }
        return generatedQuests
    }

    fun getQuest(questType: QuestType): Quest {
        if (generatedQuests.isEmpty()) {
            return Quest(
                type = questType,
                targetValue = 10,
                reward = 100
            )
        }
        return generatedQuests.first { it.type == questType }
    }

    fun getQuestProgress(player: Player, questType: QuestType): Int {
        return when (questType) {
            QuestType.KILL_COUNT -> player.totalKillsEnemy
            QuestType.STAGE_PROGRESS -> player.currentStage
            QuestType.GOLD_EARN -> player.gold
            QuestType.TOTAL_KILLS -> player.totalKills
            QuestType.UPGRADE_SKILLS -> player.upgradeSkills
        }
    }

    private fun generateQuest(player: Player, questType: QuestType): Quest {
        val target = when (questType) {
            QuestType.KILL_COUNT -> (5 + player.currentStage..20 + player.currentStage * 2).random()
            QuestType.STAGE_PROGRESS -> (1 + player.currentStage / 3..5 + player.currentStage / 2).random()
            QuestType.GOLD_EARN -> (100 + player.currentStage * 50..1000 + player.currentStage * 100).random()
            QuestType.TOTAL_KILLS -> (50 + player.enemiesDefeated..100 + player.enemiesDefeated).random()
            QuestType.UPGRADE_SKILLS -> (2..10).random()
        }

        val reward = when (questType) {
            QuestType.KILL_COUNT -> target * 2 + player.currentStage * 5
            QuestType.STAGE_PROGRESS -> target * 10 + player.currentStage * 20
            QuestType.GOLD_EARN -> (target * 0.1).toInt()
            QuestType.TOTAL_KILLS -> target / 2 + player.currentStage * 10
            QuestType.UPGRADE_SKILLS -> target * 15 + player.currentStage * 25
        }

        val targetName = if (questType == QuestType.KILL_COUNT) {
            EnemyTypes.types.random().name
        } else {
            ""
        }

        return Quest(
            targetName = targetName,
            type = questType,
            targetValue = target,
            reward = reward
        )
    }

    fun clearQuests() {
        generatedQuests = emptyList()
    }
}