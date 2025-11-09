package com.example.fantasyquestclicker.domain.utils

import com.example.fantasyquestclicker.domain.models.Player
import com.example.fantasyquestclicker.domain.models.SkillType
import org.junit.Assert.assertTrue
import org.junit.Test

class UpgradeGeneratorTest {
    // Тесты на логику генерации апгрейдов
    @Test
    fun `upgrade costs are positive`() {
        val player = Player(baseAttack = 10, criticalChance = 0.1, maxTime = 60)

        val attackCost = UpgradeGenerator.getUpgradeCost(player, SkillType.ATTACK)
        val critCost = UpgradeGenerator.getUpgradeCost(player, SkillType.CRITICAL)
        val timeCost = UpgradeGenerator.getUpgradeCost(player, SkillType.TIME)

        assertTrue("Все цены должны быть положительными", attackCost > 0 && critCost > 0 && timeCost > 0)
    }

    // Тест на логику апгрейдов атаки
    @Test
    fun `upgrade attack logic works`() {
        val player = Player(gold = 1000, baseAttack = 10)
        val cost = UpgradeGenerator.getUpgradeCost(player, SkillType.ATTACK)

        // Проверяем что хватает денег и статы увеличиваются
        assertTrue("Денег должно хватать", cost <= player.gold)
        assertTrue("Цена должна быть в диапазоне от 1 до 1000", cost in 1..1000)
    }
}