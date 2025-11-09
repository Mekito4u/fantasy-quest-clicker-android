package com.example.fantasyquestclicker.domain.utils

import com.example.fantasyquestclicker.domain.models.Player
import com.example.fantasyquestclicker.domain.models.QuestType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class QuestGeneratorTest {

    // Тестируем генерацию квестов для всех типов
    @Test
    fun `generate quests for all types`() {
        val player = Player(currentStage = 5, totalKills = 100, totalGoldEarned = 1000)

        val quests = QuestGenerator.getQuests(player)

        assertEquals("Должно быть 5 типов квестов", QuestType.entries.size, quests.size)

        quests.forEach { quest ->
            assertTrue("Квест должен иметь положительное значение цели", quest.targetValue > 0)
            assertTrue("Квест должен иметь положительное значение награды", quest.reward > 0)
        }
    }

    // Тестируем расчет прогресса для каждого типа квеста
    @Test
    fun `quest progress calculation`() {
        val player = Player(
            currentStage = 3,
            totalKillsEnemy = 15,
            gold = 500,
            totalKills = 25,
            upgradeSkills = 2
        )

        assertEquals("Прогресс убийств", 15, QuestGenerator.getQuestProgress(player, QuestType.KILL_COUNT))
        assertEquals("Прогресс этапов", 3, QuestGenerator.getQuestProgress(player, QuestType.STAGE_PROGRESS))
        assertEquals("Прогресс золота собрано", 500, QuestGenerator.getQuestProgress(player, QuestType.GOLD_EARN))
        assertEquals("Прогресс убийств всего", 25, QuestGenerator.getQuestProgress(player, QuestType.TOTAL_KILLS))
        assertEquals("Прогресс улучшений", 2, QuestGenerator.getQuestProgress(player, QuestType.UPGRADE_SKILLS))
    }
}